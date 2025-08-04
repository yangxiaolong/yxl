package com.geekhalo.feed.controller;

import com.geekhalo.feed.domain.box.BoxDao;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.OwnerType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @auther yangxiaolong
 * @create 2025/8/2
 */
@RestController
@RequestMapping("/feedJpa")
public class JpaBasedBoxDaoController {

    @Autowired
    private BoxDao boxDao;
    private FeedOwner feedOwner;
    private BoxType boxType;
    private List<FeedIndex> feedIndexList;
    private final int allData = 180;

    void setUp() {
        this.feedOwner = new FeedOwner(OwnerType.USER, RandomUtils.nextLong());
        this.boxType = BoxType.TEST_BOX;
        this.feedIndexList = Lists.newArrayList();
        for (int i = 0; i < allData; i++) {
            FeedIndex feedIndex = FeedIndex.builder()
                    .feedId(i + 1L)
                    .score(i + 1L)
                    .build();
            this.feedIndexList.add(feedIndex);
        }

        boxDao.append(feedOwner, boxType, feedIndexList);
    }

    @RequestMapping("/load")
    public void load() {
        setUp();

        int realSize = this.allData;
        long score = Long.MAX_VALUE;
        Set<FeedIndex> allFeedIndexList = Sets.newHashSet();
        List<FeedIndex> load;
        int batch = 0;
        do {
            load = boxDao.load(this.feedOwner, this.boxType, score, 10);
            score = load.stream()
                    .mapToLong(FeedIndex::getScore)
                    .min()
                    .orElse(0);
            batch++;
            allFeedIndexList.addAll(load);
        } while (!CollectionUtils.isEmpty(load));

        Assertions.assertEquals(realSize / 10 + 1, batch);

        Assertions.assertEquals(Sets.newHashSet(this.feedIndexList), allFeedIndexList);
    }

}
