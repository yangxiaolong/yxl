package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.target.LoadActionTargetByTarget;
import com.geekhalo.like.domain.user.ActionUser;
import com.geekhalo.like.domain.user.LoadActionUserByUserId;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractActionContext<CMD extends AbstractActionCommand>{
    private CMD command;

    @LoadActionUserByUserId(userId = "command.userId")
    private ActionUser actionUser;

    @LoadActionTargetByTarget(targetType = "command.targetType", targetId = "command.targetId")
    private ActionTarget actionTarget;

    protected void init(CMD command) {
        Preconditions.checkArgument(command != null);
        this.command = command;
    }

}
