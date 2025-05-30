package com.geekhalo.like.domain.target;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractSingleActionTargetLoader implements SingleActionTargetLoader{
    private final String type;

    public AbstractSingleActionTargetLoader(String type) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(type));
        this.type = type;
    }

    @Override
    public final boolean support(String type) {
        return this.type.equalsIgnoreCase(type);
    }

    @Override
    public final ActionTarget load(String type, Long id) {
        if (id == null){
            return null;
        }
        return doLoadById(type, id);
    }

    protected abstract ActionTarget doLoadById(String type, Long id);
}
