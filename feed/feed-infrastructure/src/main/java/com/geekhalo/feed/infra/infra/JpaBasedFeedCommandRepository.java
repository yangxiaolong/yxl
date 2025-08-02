package com.geekhalo.feed.infra.infra;

import com.geekhalo.feed.domain.feed.Feed;
import com.geekhalo.feed.domain.feed.FeedCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedFeedCommandRepository
    extends FeedCommandRepository, JpaRepository<Feed, Long> {
    @Override
    default Feed sync(Feed feed){
        return save(feed);
    }
}
