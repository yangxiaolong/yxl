package com.geekhalo.feed.app;

import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.feed.Feed;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.FeedQueryRepository;
import com.geekhalo.feed.domain.merger.FeedIndexMergerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomFeedQueryApplicationImpl implements CustomFeedQueryApplication {
    @Autowired
    private FeedIndexMergerService mergerService;
    @Autowired
    private FeedQueryRepository feedQueryRepository;

    @Override
    public List<Feed> queryFeeds(FeedOwner owner, QueryType queryType, Long score, Integer size) {
        List<FeedIndex> feedIndices = this.mergerService.queryFeedIndex(owner, queryType, score, size);
        List<Long> feedIds = feedIndices.stream()
                .map(FeedIndex::getFeedId)
                .collect(Collectors.toList());

        List<Feed> feeds = this.feedQueryRepository.findAllById(feedIds);
        Map<Long, Feed> feedMap = feeds.stream()
                .collect(Collectors.toMap(Feed::getId, feed -> feed));


        return feedIndices.stream()
                .map(FeedIndex::getFeedId)
                .map(feedMap::get)
                .filter(Objects::nonNull)
                .filter(Feed::isEnable)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Feed> findById(Long id) {
        return feedQueryRepository.findById(id);
    }
}
