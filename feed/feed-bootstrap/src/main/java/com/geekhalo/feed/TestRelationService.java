package com.geekhalo.feed;

import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedDataType;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.OwnerType;
import com.geekhalo.feed.domain.relation.FeedIndexBoxId;
import com.geekhalo.feed.domain.relation.FeedRelationService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestRelationService implements FeedRelationService {
    @Override
    public List<FeedIndexBoxId> getDispatcher(FeedOwner owner, FeedDataType type) {
        List<FeedIndexBoxId> feedIndexBoxIds = Lists.newArrayListWithCapacity(4);
        if (type == FeedDataType.TEST) {
            if (owner.getOwnerType() == OwnerType.USER) {
                Long id = owner.getOwnerId();
                for (int i = 0; i < 3; i++) {
                    // 将消息投放到好友的收信箱
                    FeedIndexBoxId feedIndexBoxId = new FeedIndexBoxId();
                    feedIndexBoxId.setBoxType(BoxType.IN_BOX);
                    feedIndexBoxId.setFeedOwner(new FeedOwner(OwnerType.USER, id + i + 1));
                    feedIndexBoxIds.add(feedIndexBoxId);
                }
            }
        }
        // 将消息投放到自己的发信箱
        FeedIndexBoxId feedIndexBoxId = new FeedIndexBoxId();
        feedIndexBoxId.setBoxType(BoxType.OUT_BOX);
        feedIndexBoxId.setFeedOwner(owner);
        feedIndexBoxIds.add(feedIndexBoxId);
        return feedIndexBoxIds;
    }

    @Override
    public List<FeedIndexBoxId> getMerger(FeedOwner owner, QueryType queryType) {
        List<FeedIndexBoxId> feedIndexBoxIds = Lists.newArrayListWithCapacity(4);
        if (queryType == QueryType.TEST) {
            if (OwnerType.USER == owner.getOwnerType()) {
                Long id = owner.getOwnerId();
                for (int i = 0; i < 3; i++) {
                    // 从企业的发信箱中获取信息
                    FeedIndexBoxId feedIndexBoxId = new FeedIndexBoxId();
                    feedIndexBoxId.setBoxType(BoxType.OUT_BOX);
                    feedIndexBoxId.setFeedOwner(new FeedOwner(OwnerType.COMPANY, id + i + 1));
                    feedIndexBoxIds.add(feedIndexBoxId);
                }

            }
        }
        // 从自己的收信箱获取信息
        FeedIndexBoxId feedIndexBoxId = new FeedIndexBoxId();
        feedIndexBoxId.setBoxType(BoxType.IN_BOX);
        feedIndexBoxId.setFeedOwner(owner);
        feedIndexBoxIds.add(feedIndexBoxId);

        return feedIndexBoxIds;
    }
}
