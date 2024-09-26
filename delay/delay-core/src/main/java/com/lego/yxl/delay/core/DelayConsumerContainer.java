package com.lego.yxl.delay.core;

import com.google.common.base.Preconditions;
import com.lego.yxl.delay.annotation.DelayBasedRocketMQ;
import com.lego.yxl.support.AbstractSingleMethodConsumerContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.List;


@Slf4j
public class DelayConsumerContainer extends AbstractSingleMethodConsumerContainer {
    private final DelayBasedRocketMQ delayBasedRocketMQ;


    public DelayConsumerContainer(Environment environment,DelayBasedRocketMQ delayBasedRocketMQ,Object bean, Method method) {
        super(environment, bean, method);

        Preconditions.checkArgument(delayBasedRocketMQ != null);
        this.delayBasedRocketMQ = delayBasedRocketMQ;
    }

    @Override
    protected DefaultMQPushConsumer createConsumer() throws Exception {
        // 构建 DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

        String consumerGroup = resolve(this.delayBasedRocketMQ.consumerGroup());
        String nameServerAddress = resolve(this.delayBasedRocketMQ.nameServer());

        consumer.setConsumerGroup(consumerGroup);
        consumer.setNamesrvAddr(nameServerAddress);


        // 订阅 topic
        String topic = resolve(this.delayBasedRocketMQ.topic());
        String tag = resolve(this.delayBasedRocketMQ.tag());
        consumer.subscribe(topic, tag);

        // 增加监听器
        consumer.setMessageListener(new DefaultMessageListenerOrderly());

        log.info("success to subscribe  {}, topic {}, tag {}, group {}", nameServerAddress, topic, tag, consumerGroup);
        return consumer;
    }


    private class DefaultMessageListenerOrderly implements MessageListenerOrderly {

        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
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
                        return ConsumeOrderlyStatus.SUCCESS;
                    }
                    context.setSuspendCurrentQueueTimeMillis(getDelayLevelWhenNextConsume());
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }
}
