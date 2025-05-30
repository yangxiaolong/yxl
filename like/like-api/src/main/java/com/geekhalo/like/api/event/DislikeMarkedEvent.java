package com.geekhalo.like.api.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DislikeMarkedEvent extends AbstractActionEvent{
    public static final String TAG = "DislikeMarkedEvent";
    public static DislikeMarkedEvent apply(Long userId, String targetType, Long targetId){
        DislikeMarkedEvent event = new DislikeMarkedEvent();
        event.init(userId, targetType, targetId);
        return event;
    }
}
