package com.lego.yxl.async.normal;

import com.google.common.base.Preconditions;
import com.lego.yxl.async.annotation.AsyncBasedRocketMQ;
import com.lego.yxl.support.AbstractSingleMethodConsumerContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.List;


@Slf4j
public class NormalAsyncConsumerContainer
        extends AbstractSingleMethodConsumerContainer {
    private final AsyncBasedRocketMQ asyncBasedRocketMQ;


    protected NormalAsyncConsumerContainer(Environment environment,
                                     AsyncBasedRocketMQ asyncBasedRocketMQ,
                                     Object bean,
                                     Method method) {
        super(environment, bean, method);

        Preconditions.checkArgument(asyncBasedRocketMQ != null);
        this.asyncBasedRocketMQ = asyncBasedRocketMQ;
    }


    @Override
    protected DefaultMQPushConsumer createConsumer() throws Exception {
        // 构建 DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

        String nameServer = resolve(this.asyncBasedRocketMQ.nameServer());
        consumer.setNamesrvAddr(nameServer);

        String group = resolve(this.asyncBasedRocketMQ.consumerGroup());
        consumer.setConsumerGroup(group);

        String topic = resolve(this.asyncBasedRocketMQ.topic());
        String tag = resolve(this.asyncBasedRocketMQ.tag());
        consumer.subscribe(topic, tag);

        consumer.setMessageListener(new DefaultMessageListenerConcurrently());

        log.info("success to subscribe  {}, topic {}, tag {}, group {}", nameServer, topic, tag, group);
        return consumer;
    }


    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @SuppressWarnings("unchecked")
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    invokeMethod(messageExt);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);

                    if (skipWhenException()){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    context.setDelayLevelWhenNextConsume(getDelayLevelWhenNextConsume());
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
