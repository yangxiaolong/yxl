package com.geekhalo.feed.domain.feed.create;

import com.geekhalo.feed.domain.feed.AbstractFeedEvent;
import com.geekhalo.feed.domain.feed.Feed;
import lombok.Value;

@Value
public class FeedCreatedEvent
        extends AbstractFeedEvent{
    public FeedCreatedEvent(Feed agg){
        super(agg);
    }
}
