package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractMarkedEvent;

public class DislikeMarkedEvent extends AbstractMarkedEvent<DislikeAction> {
    private DislikeMarkedEvent(DislikeAction source) {
        super(source);
    }

    public static DislikeMarkedEvent apply(DislikeAction dislikeAction){
        return new DislikeMarkedEvent(dislikeAction);
    }
}
