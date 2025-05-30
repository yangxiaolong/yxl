package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.CancelActionCommand;

public class UndislikeActionCommand extends CancelActionCommand {
    protected UndislikeActionCommand(){

    }


    public static UndislikeActionCommand apply(Long userId, String targetType, Long targetId){
        UndislikeActionCommand context = new UndislikeActionCommand();
        context.init(userId, targetType, targetId);
        return context;
    }
}
