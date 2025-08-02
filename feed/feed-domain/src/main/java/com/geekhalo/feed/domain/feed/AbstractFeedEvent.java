package com.geekhalo.feed.domain.feed;

import com.lego.yxl.command.core.support.AbstractDomainEvent;

public abstract class AbstractFeedEvent extends AbstractDomainEvent<Long, Feed> {
    public AbstractFeedEvent(Feed agg) {
        super(agg);
    }
}
