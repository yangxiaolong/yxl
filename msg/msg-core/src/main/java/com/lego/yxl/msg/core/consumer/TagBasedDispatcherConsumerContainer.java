package com.lego.yxl.msg.core.consumer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lego.yxl.msg.core.annotation.HandleTag;
import com.lego.yxl.msg.core.annotation.TagBasedDispatcherMessageConsumer;
import com.lego.yxl.support.AbstractConsumerContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TagBasedDispatcherConsumerContainer extends AbstractConsumerContainer {
    private final TagBasedDispatcherMessageConsumer tagBasedDispatcherMessageConsumer;
    private final Map<String, MethodInvoker> tagMethods = Maps.newConcurrentMap();

    public TagBasedDispatcherConsumerContainer(Environment environment,
                                               Object bean,
                                               TagBasedDispatcherMessageConsumer tagBasedDispatcherMessageConsumer) {
        super(environment, bean);
        Preconditions.checkArgument(tagBasedDispatcherMessageConsumer != null);
        this.tagBasedDispatcherMessageConsumer = tagBasedDispatcherMessageConsumer;
    }

    @Override
    protected DefaultMQPushConsumer createConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        String nameServer = resolve(this.tagBasedDispatcherMessageConsumer.nameServer());
        consumer.setNamesrvAddr(nameServer);

        String group = resolve(this.tagBasedDispatcherMessageConsumer.consumer());
        consumer.setConsumerGroup(group);

        String topic = resolve(this.tagBasedDispatcherMessageConsumer.topic());
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
        List<Method> methods = MethodUtils.getMethodsListWithAnnotation(this.bean.getClass(), HandleTag.class);
        methods.forEach(method -> {
            if (method.getParameterCount() != 1) {
                log.warn("Method {} must have only one param", method);
                return;
            }
            HandleTag handleTag = AnnotatedElementUtils.findMergedAnnotation(method, HandleTag.class);
            if (Objects.isNull(handleTag)) {
                String msg = String.format("Tag %s is null", this.bean.getClass().getName());
                throw new RuntimeException(msg);
            }
            String tag = handleTag.value();
            if (this.tagMethods.containsKey(tag)) {
                String msg = String.format("Tag %s On %s is duplicate", tag, this.bean.getClass().getName());
                throw new RuntimeException(msg);
            }
            this.tagMethods.put(tag, new MethodInvoker(method));
        });
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
