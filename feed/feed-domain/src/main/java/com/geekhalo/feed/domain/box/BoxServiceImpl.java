package com.geekhalo.feed.domain.box;

import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BoxServiceImpl implements BoxService {
    @Autowired
    private BoxDao boxDao;

    @Autowired
    private BoxCache boxCache;

    @Override
    public void append(FeedOwner feedOwner, BoxType boxType, FeedIndex feedIndex) {
        appendToCache(feedOwner, boxType, feedIndex);
        appendToDB(feedOwner, boxType, feedIndex);
    }

    private void appendToDB(FeedOwner feedOwner, BoxType boxType, FeedIndex feedIndex) {
        this.boxDao.append(feedOwner, boxType, feedIndex);
    }

    private void appendToCache(FeedOwner feedOwner, BoxType boxType, FeedIndex feedIndex) {
        this.boxCache.append(feedOwner, boxType, feedIndex);
    }

    @Override
    public List<FeedIndex> queryInboxByScore(FeedOwner feedOwner, BoxType boxType, Long score, Integer size) {
        List<FeedIndex> resultFromCache = loadFromCache(feedOwner, boxType, score, size);
        if (!CollectionUtils.isEmpty(resultFromCache)) {
            return loadFromDb(feedOwner, boxType, score, size);
        }
        return resultFromCache;
    }

    private List<FeedIndex> loadFromDb(FeedOwner feedOwner, BoxType boxType, Long score, Integer size) {
        return this.boxDao.load(feedOwner, boxType, score, size);
    }

    private List<FeedIndex> loadFromCache(FeedOwner feedOwner, BoxType boxType, Long score, Integer size) {
        return this.boxCache.load(feedOwner, boxType, score, size);
    }
}
