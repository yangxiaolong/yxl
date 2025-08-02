package com.geekhalo.feed.domain.feed.disable;

import com.geekhalo.feed.domain.feed.AbstractFeedEvent;
import com.geekhalo.feed.domain.feed.Feed;
import lombok.Value;

@Value
public class FeedDisabledEvent extends AbstractFeedEvent {
    public FeedDisabledEvent(Feed agg) {
        super(agg);
    }
}
