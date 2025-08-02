package com.geekhalo.feed.app;

import com.geekhalo.feed.domain.dispatcher.FeedIndexDispatcherService;
import com.geekhalo.feed.domain.feed.AbstractFeedEvent;
import com.geekhalo.feed.domain.feed.create.FeedCreatedEvent;
import com.geekhalo.feed.domain.feed.enable.FeedEnabledEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class FeedIndexDispatcherListener {

    @Autowired
    private FeedIndexDispatcherService dispatcherService;

    @EventListener
    public void handleFeedCreatedEvent(FeedCreatedEvent event){
        handleEvent(event);
    }

    @EventListener
    public void handleFeedEnableEvent(FeedEnabledEvent event){
        handleEvent(event);
    }

    private void handleEvent(AbstractFeedEvent feedEvent){
        this.dispatcherService.dispatcher(feedEvent.getSource());
    }
}
