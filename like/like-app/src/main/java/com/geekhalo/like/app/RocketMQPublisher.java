package com.geekhalo.like.app;

import com.alibaba.fastjson.JSON;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeCancelledEvent;
import com.geekhalo.like.domain.dislike.DislikeMarkedEvent;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeCancelledEvent;
import com.geekhalo.like.domain.like.LikeMarkedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
@ConditionalOnProperty(name = "like.event.enable", havingValue = "true", matchIfMissing = false)
@ConditionalOnMissingBean(RocketMQTemplate.class)
@ConditionalOnClass(RocketMQTemplate.class)
@Slf4j
public class RocketMQPublisher {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${like.event.topic}")
    private String topic;

    public RocketMQPublisher(){
        log.info("Use RocketMQ to Publish Like Event");
    }

    @EventListener
    public void handle(LikeMarkedEvent event){
        LikeAction likeAction = event.getSource();
        com.geekhalo.like.api.event.LikeMarkedEvent markedEvent =
                com.geekhalo.like.api.event.LikeMarkedEvent.apply(
                        likeAction.getUser().getUserId(),
                        likeAction.getTarget().getType(),
                        likeAction.getTarget().getId());
        String shardingKey = String.valueOf(likeAction.getUser().getUserId());

        syncSendEvent(com.geekhalo.like.api.event.LikeMarkedEvent.TAG, markedEvent, shardingKey);
    }

    @EventListener
    public void handle(LikeCancelledEvent event){
        LikeAction likeAction = event.getSource();
        com.geekhalo.like.api.event.LikeCancelledEvent msgEvent =
                com.geekhalo.like.api.event.LikeCancelledEvent.apply(
                        likeAction.getUser().getUserId(),
                        likeAction.getTarget().getType(),
                        likeAction.getTarget().getId());
        String shardingKey = String.valueOf(likeAction.getUser().getUserId());

        syncSendEvent(com.geekhalo.like.api.event.LikeCancelledEvent.TAG, msgEvent, shardingKey);
    }

    @EventListener
    public void handle(DislikeMarkedEvent event){
        DislikeAction likeAction = event.getSource();
        com.geekhalo.like.api.event.DislikeMarkedEvent msgEvent =
                com.geekhalo.like.api.event.DislikeMarkedEvent.apply(
                        likeAction.getUser().getUserId(),
                        likeAction.getTarget().getType(),
                        likeAction.getTarget().getId());
        String shardingKey = String.valueOf(likeAction.getUser().getUserId());

        syncSendEvent(com.geekhalo.like.api.event.DislikeMarkedEvent.TAG, msgEvent, shardingKey);
    }

    @EventListener
    public void handle(DislikeCancelledEvent event){
        DislikeAction likeAction = event.getSource();
        com.geekhalo.like.api.event.DislikeCancelledEvent msgEvent =
                com.geekhalo.like.api.event.DislikeCancelledEvent.apply(
                        likeAction.getUser().getUserId(),
                        likeAction.getTarget().getType(),
                        likeAction.getTarget().getId());
        String shardingKey = String.valueOf(likeAction.getUser().getUserId());

        syncSendEvent(com.geekhalo.like.api.event.DislikeCancelledEvent.TAG, msgEvent, shardingKey);
    }

    private void syncSendEvent(String tag, Object markedEvent, String shardingKey){
        String destination = createDestination(this.topic, tag);
        String data = JSON.toJSONString(markedEvent);
        Message<String> msg = MessageBuilder
                .withPayload(data)
                .build();
        try {
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(destination, msg, shardingKey);
            log.info("success to send msg {} to {}, msgId is {}", msg, destination, sendResult.getMsgId());
        }catch (Exception e){
            log.info("failed to send msg {} to {}", msg, destination, e);
        }

    }

    protected String createDestination(String topic, String tag) {
        if (StringUtils.isNotEmpty(tag)){
            return topic + ":" + tag;
        }else {
            return topic;
        }
    }
}
