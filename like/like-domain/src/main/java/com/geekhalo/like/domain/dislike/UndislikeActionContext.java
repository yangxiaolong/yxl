package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.CancelActionContext;

public class UndislikeActionContext extends CancelActionContext<UndislikeActionCommand> {
    protected UndislikeActionContext(){

    }


    public static UndislikeActionContext apply(UndislikeActionCommand command){
        UndislikeActionContext context = new UndislikeActionContext();
        context.init(command);
        return context;
    }
}
