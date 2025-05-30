package com.geekhalo.like.api.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LikeCancelledEvent extends AbstractActionEvent {
    public static final String TAG = "LikeCancelledEvent";

    public static LikeCancelledEvent apply(Long userId, String targetType, Long targetId){
        LikeCancelledEvent event = new LikeCancelledEvent();
        event.init(userId, targetType, targetId);
        return event;
    }
}
