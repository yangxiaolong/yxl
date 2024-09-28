package com.lego.yxl.msg.consumer;

import com.lego.yxl.msg.annotation.HandleTag;
import com.lego.yxl.msg.annotation.TagBasedDispatcherMessageConsumer;


@TagBasedDispatcherMessageConsumer(
        topic = "consumer-test-topic-2",
        consumer = "user-message-consumer-2"
)
public class AnnotationBasedUserMessageConsumer extends UserMessageConsumer {

    @HandleTag("UserCreatedEvent")
    public void handle(UserEvents.UserCreatedEvent userCreatedEvent) {
        super.handle(userCreatedEvent);
    }

    @HandleTag("UserEnableEvent")
    public void handle(UserEvents.UserEnableEvent userEnableEvent) {
        super.handle(userEnableEvent);
    }

    @HandleTag("UserDisableEvent")
    public void handle(UserEvents.UserDisableEvent userDisableEvent) {
        super.handle(userDisableEvent);
    }

    @HandleTag("UserDeletedEvent")
    public void handle(UserEvents.UserDeletedEvent userDeletedEvent) {
        super.handle(userDeletedEvent);
    }
}
