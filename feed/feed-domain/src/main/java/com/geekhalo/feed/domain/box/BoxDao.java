package com.geekhalo.feed.domain.box;

import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;

import java.util.List;

public interface BoxDao {
    void append(FeedOwner feedOwner, BoxType boxType, FeedIndex feedIndex);

    void append(FeedOwner feedOwner, BoxType boxType, List<FeedIndex> feedIndices);

     List<FeedIndex> load(FeedOwner feedOwner, BoxType boxType, Long score, Integer size);
}
