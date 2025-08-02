package com.geekhalo.feed.domain.feed.enable;

import com.geekhalo.feed.domain.feed.AbstractFeedEvent;
import com.geekhalo.feed.domain.feed.Feed;
import lombok.Value;

@Value
public class FeedEnabledEvent extends AbstractFeedEvent {

    public FeedEnabledEvent(Feed agg) {
        super(agg);
    }

}
