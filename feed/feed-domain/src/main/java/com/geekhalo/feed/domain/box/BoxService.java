package com.geekhalo.feed.domain.box;

import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;

import java.util.List;

public interface BoxService {
    void append(FeedOwner feedOwner, BoxType boxType, FeedIndex feedIndex);

    List<FeedIndex> queryInboxByScore(FeedOwner feedOwner, BoxType boxType, Long score, Integer size);
}
