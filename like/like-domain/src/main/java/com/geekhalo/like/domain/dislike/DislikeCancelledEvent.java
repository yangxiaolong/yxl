package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractCancelledEvent;

public class DislikeCancelledEvent extends AbstractCancelledEvent<DislikeAction> {
    private DislikeCancelledEvent(DislikeAction source) {
        super(source);
    }

    public static DislikeCancelledEvent apply(DislikeAction dislikeAction){
        return new DislikeCancelledEvent(dislikeAction);
    }
}
