package com.geekhalo.like.infra.support;

import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;

import java.util.List;

public interface TargetCountCache<C extends AbstractTargetCount> {
    void incr(ActionTarget target, long count);

    void sync(C entity);

    List<C> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds);
}
