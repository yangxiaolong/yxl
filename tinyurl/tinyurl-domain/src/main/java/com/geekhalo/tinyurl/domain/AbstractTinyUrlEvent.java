package com.geekhalo.tinyurl.domain;

import com.lego.yxl.command.core.support.AbstractDomainEvent;

public abstract class AbstractTinyUrlEvent extends AbstractDomainEvent<Long, TinyUrl> {
    public AbstractTinyUrlEvent(TinyUrl source) {
        super(source);
    }
}
