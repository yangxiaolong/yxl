package com.geekhalo.feed.domain.merger;

import com.geekhalo.feed.domain.feed.FeedIndex;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FeedIndexMergerTest {

    @Test
    public void testGetMergedResult() {
        int maxSize = 5;
        FeedIndexMerger merger = new FeedIndexMerger(maxSize);

        {
            List<FeedIndex> feedIndices = createFeedIndices(10, maxSize);
            merger.merge(feedIndices);
        }

        {
            List<FeedIndex> feedIndices = createFeedIndices(30, maxSize);
            merger.merge(feedIndices);
        }

        {
            List<FeedIndex> feedIndices = createFeedIndices(20, maxSize);
            merger.merge(feedIndices);
        }


        // When
        List<FeedIndex> result = merger.getMergedResult();

        // Then
        Assertions.assertEquals(maxSize, result.size());
        for (int i = 0; i < maxSize; i++) {
            FeedIndex feedIndex = result.get(i);
            Assertions.assertEquals(30L + maxSize - i, feedIndex.getFeedId());
        }
    }

    private List<FeedIndex> createFeedIndices(int startId, int maxSize) {
        List<FeedIndex> feedIndices = Lists.newArrayListWithCapacity(maxSize);
        for (int i = 0; i < maxSize; i++) {
            feedIndices.add(new FeedIndex(startId + i + 1L, (startId + i) * 10L, 3L));
        }
        return feedIndices;
    }
}