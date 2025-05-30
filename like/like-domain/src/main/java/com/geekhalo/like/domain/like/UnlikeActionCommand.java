package com.geekhalo.like.domain.like;


import com.geekhalo.like.domain.CancelActionCommand;

public class UnlikeActionCommand extends CancelActionCommand {
    protected UnlikeActionCommand(){

    }


    public static UnlikeActionCommand apply(Long userId, String targetType, Long targetId){
        UnlikeActionCommand context =  new UnlikeActionCommand();
        context.init(userId, targetType, targetId);
        return context;
    }
}
