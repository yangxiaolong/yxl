package com.geekhalo.like.api.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DislikeCancelledEvent extends AbstractActionEvent {
    public static final String TAG = "DislikeCancelledEvent";

    public static DislikeCancelledEvent apply(Long userId, String targetType, Long targetId) {
        DislikeCancelledEvent event = new DislikeCancelledEvent();
        event.init(userId, targetType, targetId);
        return event;
    }
}
