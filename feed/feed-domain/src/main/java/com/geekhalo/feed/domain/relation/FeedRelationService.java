package com.geekhalo.feed.domain.relation;

import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.feed.FeedDataType;
import com.geekhalo.feed.domain.feed.FeedOwner;

import java.util.List;

public interface FeedRelationService {
    /**
     * 获取待分发的 Box 信息，主要用于推模式
     * @param owner
     * @param type
     * @return
     */
    List<FeedIndexBoxId> getDispatcher(FeedOwner owner, FeedDataType type);

    /**
     *  获取待合并的 Box 信息，主要用于拉模式
     * @param owner
     * @param queryType
     * @return
     */
    List<FeedIndexBoxId> getMerger(FeedOwner owner, QueryType queryType);
}
