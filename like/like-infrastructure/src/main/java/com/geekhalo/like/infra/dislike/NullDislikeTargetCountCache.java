package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.infra.support.NullTargetCountCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NullDislikeTargetCountCache extends NullTargetCountCache<DislikeTargetCount> {
    @Autowired
    private DislikeTargetCountDao dao;

    @Override
    protected List<DislikeTargetCount> loadByTargetTypeAndTargetIdIn(String type, List<Long> targetIds) {
        return dao.getByTargetTypeAndTargetIdIn(type, targetIds);
    }
}
