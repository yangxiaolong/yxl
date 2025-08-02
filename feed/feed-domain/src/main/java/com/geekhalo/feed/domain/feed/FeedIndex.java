package com.geekhalo.feed.domain.feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedIndex implements Comparable{
    private Long feedId;
    private Long score;
    private Long ownerId;

    @Override
    public int compareTo(Object o) {
        FeedIndex feedIndex = (FeedIndex) o;
        return  (int) (this.getScore() - feedIndex.getScore());
    }
}
