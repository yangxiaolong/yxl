package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.ActionStatus;
import com.geekhalo.like.domain.MarkActionCommand;

public class DislikeActionCommand extends MarkActionCommand {
    protected DislikeActionCommand() {

    }

    public static DislikeActionCommand apply(Long userId, String targetType, Long targetId, ActionStatus status) {
        DislikeActionCommand context = new DislikeActionCommand();
        context.init(userId, targetType, targetId, status);
        return context;
    }
}
