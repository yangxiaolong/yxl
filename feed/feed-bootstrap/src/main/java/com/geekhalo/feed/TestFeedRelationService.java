package com.geekhalo.feed;

import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedDataType;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.OwnerType;
import com.geekhalo.feed.domain.relation.FeedIndexBoxId;
import com.geekhalo.feed.domain.relation.FeedRelationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Primary
public class TestFeedRelationService implements FeedRelationService {

    @Override
    public List<FeedIndexBoxId> getDispatcher(FeedOwner owner, FeedDataType type) {
        if (owner.getOwnerType().equals(OwnerType.TEST)){
            List<FeedIndexBoxId> feedIndexBoxIds = IntStream.range(0, 10)
                    .mapToObj(i -> {
                        FeedOwner feedOwner = new FeedOwner();
                        feedOwner.setOwnerType(OwnerType.USER);
                        feedOwner.setOwnerId(owner.getOwnerId() + i + 1);
                        return new FeedIndexBoxId(feedOwner, BoxType.IN_BOX);
                    })
                    .collect(Collectors.toList());
            FeedOwner feedOwner = new FeedOwner(OwnerType.USER, owner.getOwnerId());
            FeedIndexBoxId ownerBoxId = new FeedIndexBoxId(feedOwner, BoxType.OUT_BOX);
            feedIndexBoxIds.add(ownerBoxId);
            return feedIndexBoxIds;
        }
        return null;
    }

    @Override
    public List<FeedIndexBoxId> getMerger(FeedOwner owner, QueryType queryType) {
        if(queryType == QueryType.TEST){
            return IntStream.range(0, 10)
                    .mapToObj(i -> {
                        FeedOwner feedOwner = new FeedOwner();
                        feedOwner.setOwnerType(OwnerType.USER);
                        feedOwner.setOwnerId(owner.getOwnerId() + i + 1);
                        return new FeedIndexBoxId(feedOwner, BoxType.OUT_BOX);
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }
}
