package com.geekhalo.feed.controller;

import com.geekhalo.feed.domain.box.BoxCache;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.OwnerType;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2025/8/2
 */
@RestController
@RequestMapping("/feedRedis")
public class RedisBasedBoxCacheController {

    @Autowired
    private BoxCache boxCache;
    private FeedOwner feedOwner;
    private BoxType boxType;
    private List<FeedIndex> feedIndexList;
    private int allData = 1500;

    void setUp() {
        this.feedOwner = new FeedOwner(OwnerType.USER, 100L);
        this.boxType = BoxType.IN_BOX;
        this.feedIndexList = Lists.newArrayList();
        for (int i = 0; i < allData; i++) {
            FeedIndex feedIndex = FeedIndex.builder()
                    .feedId(i + 1L)
                    .score(i + 1L)
                    .build();
            this.feedIndexList.add(feedIndex);
        }
        for (FeedIndex feedIndex : this.feedIndexList) {
            boxCache.append(this.feedOwner, this.boxType, feedIndex);
        }
    }

    @RequestMapping("/load")
    public void load() {
        setUp();

        int realSize = Math.min(this.allData, this.boxType.getMaxCacheLength());
        Long score = Long.MAX_VALUE;
        List<FeedIndex> load = null;
        int batch = 0;
        do {
            load = boxCache.load(this.feedOwner, this.boxType, score, 10);
            score = load.stream()
                    .mapToLong(FeedIndex::getScore)
                    .min()
                    .orElse(0);
            batch++;
        } while (!CollectionUtils.isEmpty(load));

        Assertions.assertEquals(realSize / 10 + 1, batch);
    }

}
