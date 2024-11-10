package com.lego.yxl.async.core.order;

import com.google.common.base.Preconditions;
import com.lego.yxl.async.core.annotation.AsyncForOrderedBasedRocketMQ;
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
public class OrderedAsyncConsumerContainer extends AbstractSingleMethodConsumerContainer {
    private final AsyncForOrderedBasedRocketMQ asyncForOrderedBasedRocketMQ;

    public OrderedAsyncConsumerContainer(Environment environment,
                                         AsyncForOrderedBasedRocketMQ asyncForOrderedBasedRocketMQ,
                                         Object bean,
                                         Method method) {
        super(environment, bean, method);

        Preconditions.checkArgument(asyncForOrderedBasedRocketMQ != null);
        this.asyncForOrderedBasedRocketMQ = asyncForOrderedBasedRocketMQ;
    }

    @Override
    protected DefaultMQPushConsumer createConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        String nameServer = resolve(this.asyncForOrderedBasedRocketMQ.nameServer());
        consumer.setNamesrvAddr(nameServer);

        String group = resolve(this.asyncForOrderedBasedRocketMQ.consumerGroup());
        consumer.setConsumerGroup(group);

        String topic = resolve(this.asyncForOrderedBasedRocketMQ.topic());
        String tag = resolve(this.asyncForOrderedBasedRocketMQ.tag());
        consumer.subscribe(topic, tag);

        consumer.setMessageListener(new DefaultMessageListenerOrderly());

        log.info("success to subscribe  {}, topic {}, tag {}, group {}", nameServer, topic, tag, group);
        return consumer;
    }

    public class DefaultMessageListenerOrderly implements MessageListenerOrderly {

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
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}",
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
}
