package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractCancelledEvent;

public class LikeCancelledEvent
    extends AbstractCancelledEvent<LikeAction> {
    private LikeCancelledEvent(LikeAction source) {
        super(source);
    }

    public static LikeCancelledEvent apply(LikeAction likeAction){
        return new LikeCancelledEvent(likeAction);
    }
}
