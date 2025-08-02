package com.geekhalo.feed.domain.merger;

import com.geekhalo.feed.domain.feed.FeedIndex;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class FeedIndexMerger {
    private final int maxSize;
    private final PriorityQueue<FeedIndex> feedIndexQueue;
    public FeedIndexMerger(int maxSize) {
        this.maxSize = maxSize;
        this.feedIndexQueue = new PriorityQueue<>();
    }

    public void merge(List<FeedIndex> feedIndices) {
        feedIndices.forEach(feedIndex -> {
            this.feedIndexQueue.offer(feedIndex);
        });

        while (feedIndexQueue.size() > this.maxSize){
            this.feedIndexQueue.poll();
        }
    }

    public List<FeedIndex> getMergedResult(){
        List<FeedIndex> result = Lists.newArrayListWithCapacity(this.maxSize);
        while (!feedIndexQueue.isEmpty()) {
            result.add(feedIndexQueue.poll());
        }
        Collections.reverse(result);
        return result;
    }
}
