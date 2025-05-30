package com.geekhalo.like.infra.support;

import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;

import java.util.List;

public abstract class NullTargetCountCache<C extends AbstractTargetCount>
    implements TargetCountCache<C>{

    @Override
    public void incr(ActionTarget target, long count) {

    }

    @Override
    public void sync(C entity) {

    }

    @Override
    public List<C> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds) {
        return loadByTargetTypeAndTargetIdIn(type, targetIds);
    }

    protected abstract List<C> loadByTargetTypeAndTargetIdIn(String type, List<Long> targetIds);
}
