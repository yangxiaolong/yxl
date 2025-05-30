package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;
import lombok.Value;

@Value
public class ActionKey {
    private ActionUser actionUser;
    private ActionTarget actionTarget;

    public static ActionKey apply(Long userId, String targetType, Long targetId){
        ActionUser user = ActionUser.apply(userId);
        ActionTarget target = ActionTarget.apply(targetType, targetId);
        return new ActionKey(user, target);
    }
}
