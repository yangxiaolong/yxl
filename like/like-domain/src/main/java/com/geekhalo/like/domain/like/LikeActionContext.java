package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.MarkActionContext;

public class LikeActionContext extends MarkActionContext<LikeActionCommand> {
    protected LikeActionContext(){

    }


    public static LikeActionContext apply(LikeActionCommand command){
        LikeActionContext context = new LikeActionContext();
        context.init(command);
        return context;
    }
}
