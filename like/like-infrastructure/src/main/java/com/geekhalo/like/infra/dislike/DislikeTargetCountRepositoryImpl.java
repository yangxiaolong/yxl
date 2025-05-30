package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.infra.support.TargetCountCache;
import com.lego.yxl.async.core.annotation.AsyncForOrderedBasedRocketMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DislikeTargetCountRepositoryImpl
        implements DislikeTargetCountRepository {

    @Autowired
    private DislikeTargetCountDao dao;

    @Autowired
    private TargetCountCache<DislikeTargetCount> cache;


    @Override
    public Optional<DislikeTargetCount> getByTarget(ActionTarget target) {
        return this.dao.getByTarget(target);
    }

    @Override
    public void incr(ActionTarget target, long count) {
        this.cache.incr(target, count);
        Object o = AopContext.currentProxy();
        if (o instanceof DislikeTargetCountRepositoryImpl){
            ((DislikeTargetCountRepositoryImpl)o).incrForDB(target, count);
        }else {
            this.incrForDB(target, count);
        }
    }

    @AsyncForOrderedBasedRocketMQ(
            enable = "${target.count.dislike.async.enable}",
            topic = "${target.count.dislike.async.topic}",
            tag = "TargetCountAsyncIncrForDislike",
            shardingKey = "#target.id",
            consumerGroup = "${target.count.dislike.async.consumerGroup}")
    public void incrForDB(ActionTarget target, long count) {
        log.info("begin to incr for db target {}, count {}", target, count);
        this.dao.incr(target, count);
        log.info("success to incr for db target {}, count {}", target, count);
    }

    @Override
    public List<DislikeTargetCount> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds) {
        return this.cache.getByTargetTypeAndTargetIdIn(type, targetIds);
    }

    @Override
    public DislikeTargetCount sync(DislikeTargetCount entity) {
        this.cache.sync(entity);
        return this.dao.save(entity);
    }

    @Override
    public Optional<DislikeTargetCount> findById(Long aLong) {
        return this.dao.findById(aLong);
    }
}
