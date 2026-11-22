package com.lego.yxl.msg.consumer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = "consumer-test-topic-1",
        consumerGroup = "user-message-consumer-1",
        selectorExpression = "*",
        //第二点靠消费模式。原生写法是 `@RocketMQMessageListener`
        // 上设置 `consumeMode = ConsumeMode.ORDERLY`，
        // 并实现 `RocketMQListener<MessageExt>`。
        consumeMode = ConsumeMode.ORDERLY
)
@Slf4j
public class RocketBasedUserMessageConsumer extends UserMessageConsumer
        implements RocketMQListener<MessageExt> {

    /**
     第一，tag 的维护成本高
     第二，模板代码淹没业务
     第三，API 侵入
     */
    @Override
    public void onMessage(MessageExt message) {
        String tag = message.getTags();
        byte[] body = message.getBody();
        log.info("handle msg body {}", new String(body));
        switch (tag) {
            case "UserCreatedEvent" -> {
                UserEvents.UserCreatedEvent createdEvent =
                        JSON.parseObject(body, UserEvents.UserCreatedEvent.class);
                handle(createdEvent);
            }
            case "UserEnableEvent" -> {
                UserEvents.UserEnableEvent enableEvent =
                        JSON.parseObject(body, UserEvents.UserEnableEvent.class);
                handle(enableEvent);
            }
            case "UserDisableEvent" -> {
                UserEvents.UserDisableEvent disableEvent =
                        JSON.parseObject(body, UserEvents.UserDisableEvent.class);
                handle(disableEvent);
            }
            case "UserDeletedEvent" -> {
                UserEvents.UserDeletedEvent deletedEvent =
                        JSON.parseObject(body, UserEvents.UserDeletedEvent.class);
                handle(deletedEvent);
            }
        }
    }
}
