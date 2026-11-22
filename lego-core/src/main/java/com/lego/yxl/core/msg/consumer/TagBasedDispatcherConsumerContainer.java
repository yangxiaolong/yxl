package com.lego.yxl.core.msg.consumer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lego.yxl.core.msg.annotation.HandleTag;
import com.lego.yxl.core.msg.annotation.TagBasedDispatcherMessageConsumer;
import com.lego.yxl.core.singlequery.support.AbstractConsumerContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Slf4j
public class TagBasedDispatcherConsumerContainer extends AbstractConsumerContainer {
    private final TagBasedDispatcherMessageConsumer tagBasedDispatcherMessageConsumer;
    private final Map<String, MethodInvoker> tagMethods = Maps.newConcurrentMap();

    public TagBasedDispatcherConsumerContainer(
            Environment environment,
            Object bean,
            TagBasedDispatcherMessageConsumer tagBasedDispatcherMessageConsumer) {
        super(environment, bean);
        Preconditions.checkArgument(tagBasedDispatcherMessageConsumer != null);
        this.tagBasedDispatcherMessageConsumer = tagBasedDispatcherMessageConsumer;
    }

    @Override
    protected DefaultMQPushConsumer createConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

        //1
        String nameServer = resolve(this.tagBasedDispatcherMessageConsumer.nameServer());
        consumer.setNamesrvAddr(nameServer);

        String group = resolve(this.tagBasedDispatcherMessageConsumer.consumer());
        consumer.setConsumerGroup(group);

        String topic = resolve(this.tagBasedDispatcherMessageConsumer.topic());

        //2 findTag()` 把 map 里所有 tag 用 `||` 拼起来，一次性精准订阅，
        // 例如 `UserCreatedEvent||UserEnableEvent||UserDisableEvent||UserDeletedEvent`。
        // 这正好解决了前面说的「`*` 全量拉取浪费带宽」的问题。
        String tag = findTag();

        consumer.subscribe(topic, tag);
        consumer.setMessageListener(new DefaultMessageListenerOrderly());

        log.info("success to subscribe  {}, topic {}, tag {}, group {}", nameServer, topic, tag, group);
        return consumer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initMethods();
        super.afterPropertiesSet();
    }

    private void initMethods() {
        // 使用代理背后的真实类型扫描注解，避免 CGLIB 代理覆盖方法后 @HandleTag 丢失
        //1 用 `AopUtils.getTargetClass(this.bean)` 拿真实类型，
        //  用 `MethodUtils.getMethodsListWithAnnotation` 找出所有 `@HandleTag` 方法
        Class<?> targetClass = AopUtils.getTargetClass(this.bean);
        List<Method> methods = MethodUtils.getMethodsListWithAnnotation(targetClass, HandleTag.class);

        methods.forEach(method -> {
            //2 校验方法必须**只有一个参数**，否则告警并跳过；
            if (method.getParameterCount() != 1) {
                log.warn("Method {} must have only one param", method);
                return;
            }

            //3 解析 tag：取参数类型的 `getSimpleName()`，为空直接抛异常；
            String tag = resolveTag(method);
            if (tag.isEmpty()) {
                String msg = String.format("Tag is null or empty for method %s", method.getName());
                throw new RuntimeException(msg);
            }

            //4 校验 tag **不能重复**，重复直接抛异常；
            if (this.tagMethods.containsKey(tag)) {
                String msg = String.format("Tag %s On %s is duplicate", tag, targetClass.getName());
                throw new RuntimeException(msg);
            }
            //5 然后把 `tag -> MethodInvoker` 放进 `ConcurrentMap`。
            // 这里的 `MethodInvoker` 提前缓存了 `Method` 和泛型参数类型，运行期就不用再反射解析类型了。
            this.tagMethods.put(tag, new MethodInvoker(method));
        });

        //6 如果一个 `@HandleTag` 都没有，直接抛异常，快速失败。
        if (this.tagMethods.isEmpty()) {
            String msg = String.format("No @HandleTag method found on %s", targetClass.getName());
            throw new RuntimeException(msg);
        }
    }

    /**
     * 解析方法的 Tag 值
     * 如果 @HandleTag 指定了值则使用该值，否则使用方法参数的简单类名
     */
    private String resolveTag(Method method) {
        Class<?> paramType = method.getParameterTypes()[0];
        return paramType.getSimpleName();
    }

    private String findTag() {
        if (this.tagMethods.isEmpty()) {
            return "*";
        }
        return String.join("||", this.tagMethods.keySet());
    }

    private class DefaultMessageListenerOrderly implements MessageListenerOrderly {
        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            //分发链路是：**取 tag → 查 map → 反序列化 → 反射调用**。
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    String tags = messageExt.getTags();
                    for (String tag : tags.split("\\|\\|")) {
                        MethodInvoker methodInvoker = tagMethods.get(tag);
                        if (methodInvoker == null) {
                            log.warn("Failed to find Invoker for Tag {}", tag);
                            continue;
                        }

                        //invoke()
                        methodInvoker.invoke(messageExt.getBody());
                    }
                    long costTime = System.currentTimeMillis() - now;
                    log.info("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsume Times:{}",
                            messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);

                    if (skipWhenException()) {
                        return ConsumeOrderlyStatus.SUCCESS;
                    }

                    context.setSuspendCurrentQueueTimeMillis(getDelayLevelWhenNextConsume());
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

    class MethodInvoker {
        private final Method method;
        private final Type paraType;

        MethodInvoker(Method method) {
            this.method = method;
            this.paraType = method.getGenericParameterTypes()[0];
        }

        public void invoke(byte[] body) throws Exception {
            Object param = deserialize(body, this.paraType);
            this.method.invoke(getBean(), param);
        }

        private Object deserialize(byte[] body, Type paramType) {
            return JSON.parseObject(body, paramType);
        }

    }
}
