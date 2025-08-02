package com.geekhalo.feed.domain.relation;

import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedOwner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedIndexBoxId {
    private FeedOwner feedOwner;
    private BoxType boxType;
}
