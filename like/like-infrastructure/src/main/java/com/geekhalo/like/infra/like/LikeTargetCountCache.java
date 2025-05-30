package com.geekhalo.like.infra.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.infra.support.RedisBasedTargetCountCache;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class LikeTargetCountCache
        extends RedisBasedTargetCountCache<LikeTargetCount> {
    @Autowired
    private LikeTargetCountDao dao;

    @Value("${target.count.like.enable:false}")
    @Getter
    private boolean cacheEnable;

    @Value("${target.count.like.key:like-{type}-{id}-count}")
    private String cacheKeyTemplate;

    @Override
    protected String cacheKeyTemplate() {
        return cacheKeyTemplate;
    }


    @Override
    protected List<LikeTargetCount> loadByTargetTypeAndTargetIdIn(String type, List<Long> ids) {
        return dao.getByTargetTypeAndTargetIdIn(type, ids);
    }

    @Override
    protected LikeTargetCount buildResult(String type, Long targetId, Long count) {
        return LikeTargetCount.create(ActionTarget.apply(type, targetId), count);
    }

}
