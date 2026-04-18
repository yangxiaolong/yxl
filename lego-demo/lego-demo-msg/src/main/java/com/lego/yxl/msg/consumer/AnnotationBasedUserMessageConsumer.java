package com.lego.yxl.msg.consumer;

import com.lego.yxl.core.msg.annotation.HandleTag;
import com.lego.yxl.core.msg.annotation.TagBasedDispatcherMessageConsumer;


@TagBasedDispatcherMessageConsumer(
        topic = "consumer-test-topic-2",
        consumer = "user-message-consumer-2"
)
public class AnnotationBasedUserMessageConsumer extends UserMessageConsumer {

    @HandleTag
    public void handle(UserEvents.UserCreatedEvent userCreatedEvent) {
        super.handle(userCreatedEvent);
    }

    @HandleTag
    public void handle(UserEvents.UserEnableEvent userEnableEvent) {
        super.handle(userEnableEvent);
    }

    @HandleTag
    public void handle(UserEvents.UserDisableEvent userDisableEvent) {
        super.handle(userDisableEvent);
    }

    @HandleTag
    public void handle(UserEvents.UserDeletedEvent userDeletedEvent) {
        super.handle(userDeletedEvent);
    }
}
