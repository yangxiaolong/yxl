package com.geekhalo.like.domain;

import com.lego.yxl.core.command.support.AbstractDomainEvent;

public abstract class AbstractMarkedEvent<AGG extends AbstractAction>
        extends AbstractDomainEvent<Long, AGG> {
    public AbstractMarkedEvent(AGG source) {
        super(source);
    }
}
