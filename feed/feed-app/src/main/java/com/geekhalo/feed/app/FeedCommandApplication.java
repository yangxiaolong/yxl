package com.geekhalo.feed.app;

import com.geekhalo.feed.domain.feed.Feed;
import com.geekhalo.feed.domain.feed.FeedCommandRepository;
import com.geekhalo.feed.domain.feed.create.CreateFeedCommand;
import com.geekhalo.feed.domain.feed.disable.DisableFeedCommand;
import com.geekhalo.feed.domain.feed.enable.EnableFeedCommand;
import com.lego.yxl.command.core.CommandApplicationServiceDefinition;
import com.lego.yxl.command.core.web.AutoRegisterWebController;

@CommandApplicationServiceDefinition(
        domainClass = Feed.class,
        repositoryClass = FeedCommandRepository.class
)
@AutoRegisterWebController(name = "feedCommand")
public interface FeedCommandApplication {

    Feed create(CreateFeedCommand command);

    Feed enable(EnableFeedCommand command);

    Feed disable(DisableFeedCommand command);
}
