package com.geekhalo.like.infra.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.infra.support.NullTargetCountCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NullLikeTargetCountCache extends NullTargetCountCache<LikeTargetCount> {
    @Autowired
    private LikeTargetCountDao dao;

    @Override
    protected List<LikeTargetCount> loadByTargetTypeAndTargetIdIn(String type, List<Long> targetIds) {
        return dao.getByTargetTypeAndTargetIdIn(type, targetIds);
    }
}
