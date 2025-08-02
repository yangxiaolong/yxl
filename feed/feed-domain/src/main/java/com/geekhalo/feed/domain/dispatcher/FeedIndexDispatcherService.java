package com.geekhalo.feed.domain.dispatcher;

import com.geekhalo.feed.domain.box.BoxService;
import com.geekhalo.feed.domain.feed.Feed;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.relation.FeedIndexBoxId;
import com.geekhalo.feed.domain.relation.FeedRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FeedIndexDispatcherService {
    @Autowired
    private BoxService boxService;

    @Autowired
    private FeedRelationService relationService;

    public void dispatcher(Feed feed) {
        FeedIndex index = feed.createIndex();

        List<FeedIndexBoxId> feedIndexBoxIds = this.relationService.getDispatcher(feed.getOwner(),
                feed.getData().getType());
        for (FeedIndexBoxId feedIndexBoxId  : feedIndexBoxIds) {
            this.boxService.append(feedIndexBoxId.getFeedOwner(), feedIndexBoxId.getBoxType(), index);
            log.info("success to append index {} to  box {}-{}",
                    index,
                    feedIndexBoxId.getBoxType(),
                    feedIndexBoxId.getFeedOwner());
        }
    }
}
