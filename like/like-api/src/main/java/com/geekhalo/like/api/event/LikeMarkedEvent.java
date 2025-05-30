package com.geekhalo.like.api.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LikeMarkedEvent extends AbstractActionEvent{
    public static final String TAG = "LikeMarkedEvent";

    public static LikeMarkedEvent apply(Long userId, String targetType, Long targetId){
        LikeMarkedEvent event = new LikeMarkedEvent();
        event.init(userId, targetType, targetId);
        return event;
    }
}
