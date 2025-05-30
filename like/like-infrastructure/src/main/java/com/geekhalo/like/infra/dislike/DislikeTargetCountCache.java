package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.infra.support.RedisBasedTargetCountCache;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class DislikeTargetCountCache extends RedisBasedTargetCountCache<DislikeTargetCount> {

    @Autowired
    private DislikeTargetCountDao dao;

    @Value("${target.count.dislike.enable:false}")
    @Getter
    private boolean cacheEnable;

    @Value("${target.count.dislike.key:dislike-{type}-{id}-count}")
    private String cacheKeyTemplate;

    @Override
    protected String cacheKeyTemplate() {
        return cacheKeyTemplate;
    }

    @Override
    protected List<DislikeTargetCount> loadByTargetTypeAndTargetIdIn(String type, List ids) {
        return this.dao.getByTargetTypeAndTargetIdIn(type, ids);
    }

    @Override
    protected DislikeTargetCount buildResult(String type, Long targetId, Long count) {
        return DislikeTargetCount.create(ActionTarget.apply(type, targetId), count);
    }

}
