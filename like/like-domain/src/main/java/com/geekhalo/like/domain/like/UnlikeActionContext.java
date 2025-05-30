package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractActionContext;

public class UnlikeActionContext extends AbstractActionContext<UnlikeActionCommand> {
    protected UnlikeActionContext(){

    }


    public static UnlikeActionContext apply(UnlikeActionCommand command){
        UnlikeActionContext context = new UnlikeActionContext();
        context.init(command);
        return context;
    }
}
