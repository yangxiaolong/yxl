package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.MarkActionCommand;

public class LikeActionCommand extends MarkActionCommand {
    protected LikeActionCommand() {

    }

    public static LikeActionCommand apply(Long userId, String targetType, Long targetId) {
        LikeActionCommand context = new LikeActionCommand();
        context.init(userId, targetType, targetId);
        return context;
    }
}
