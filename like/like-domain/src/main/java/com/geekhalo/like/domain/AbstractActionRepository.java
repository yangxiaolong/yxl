package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;
import com.lego.yxl.command.core.CommandWithKeyRepository;

import java.util.List;
import java.util.Optional;

public interface AbstractActionRepository<A extends AbstractAction>
        extends CommandWithKeyRepository<A, Long, ActionKey>
        /*,QueryRepository<A, Long>*/ {
    Optional<A> getByUserAndTarget(ActionUser user, ActionTarget target);

    List<A> getByUserAndTargetType(ActionUser user, String type);

    @Override
    default Optional<A> findByKey(ActionKey key) {
        return getByUserAndTarget(key.getActionUser(), key.getActionTarget());
    }
}
