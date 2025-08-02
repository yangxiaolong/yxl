package com.geekhalo.feed.app;

import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.feed.Feed;
import com.geekhalo.feed.domain.feed.FeedOwner;

import java.util.List;
import java.util.Optional;

public interface CustomFeedQueryApplication {

    default List<Feed> queryFeeds1(FeedOwner owner, QueryType queryType, Integer size){
        return queryFeeds(owner, queryType, Long.MAX_VALUE, size);
    }

    List<Feed> queryFeeds(FeedOwner owner, QueryType queryType, Long score, Integer size);

    Optional<Feed> findById(Long id);
}
