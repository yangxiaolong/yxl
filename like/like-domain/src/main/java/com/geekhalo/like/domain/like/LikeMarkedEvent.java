package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractMarkedEvent;

public class LikeMarkedEvent
    extends AbstractMarkedEvent<LikeAction> {
    private LikeMarkedEvent(LikeAction source) {
        super(source);
    }

    public static LikeMarkedEvent apply(LikeAction likeAction){
        return new LikeMarkedEvent(likeAction);
    }
}
