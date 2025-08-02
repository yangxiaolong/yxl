package com.geekhalo.feed.domain.merger;

import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.box.BoxService;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.relation.FeedIndexBoxId;
import com.geekhalo.feed.domain.relation.FeedRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedIndexMergerService {
    @Autowired
    private BoxService boxService;

    @Autowired
    private FeedRelationService relationService;

    public List<FeedIndex> queryFeedIndex(FeedOwner owner, QueryType queryType){
        return this.queryFeedIndex(owner, queryType, 50);
    }

    public List<FeedIndex> queryFeedIndex(FeedOwner owner, QueryType queryType, Integer size){
        return this.queryFeedIndex(owner, queryType, Long.MAX_VALUE, size);
    }

    public List<FeedIndex> queryFeedIndex(FeedOwner owner, QueryType queryType, Long score, Integer size){
        List<FeedIndexBoxId> feedIndexBoxIds = this.relationService.getMerger(owner, queryType);
        return this.merge(feedIndexBoxIds, score, size);
    }

    private List<FeedIndex> merge(List<FeedIndexBoxId> boxIds, Long score, Integer size){
        FeedIndexMerger feedIndexMerger = new FeedIndexMerger(size);
        for (FeedIndexBoxId boxId : boxIds) {
            List<FeedIndex> feedIndices = this.boxService.queryInboxByScore(boxId.getFeedOwner(), boxId.getBoxType(), score, size);
            feedIndexMerger.merge(feedIndices);
        }
        return feedIndexMerger.getMergedResult();
    }

}
