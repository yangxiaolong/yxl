package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.MarkActionContext;

public class DislikeActionContext extends MarkActionContext<DislikeActionCommand> {
    protected DislikeActionContext(){

    }


    public static DislikeActionContext apply(DislikeActionCommand command){
        DislikeActionContext context = new DislikeActionContext();
        context.init(command);
        return context;
    }
}
