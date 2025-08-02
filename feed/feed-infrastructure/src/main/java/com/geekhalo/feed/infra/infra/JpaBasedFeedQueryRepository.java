package com.geekhalo.feed.infra.infra;

import com.geekhalo.feed.domain.feed.Feed;
import com.geekhalo.feed.domain.feed.FeedQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedFeedQueryRepository
    extends FeedQueryRepository, JpaRepository<Feed, Long> {
}
