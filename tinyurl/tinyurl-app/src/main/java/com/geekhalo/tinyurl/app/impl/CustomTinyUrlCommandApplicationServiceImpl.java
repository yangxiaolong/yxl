package com.geekhalo.tinyurl.app.impl;

import com.geekhalo.tinyurl.app.CustomTinyUrlCommandApplicationService;
import com.geekhalo.tinyurl.domain.IncrAccessCountCommand;
import com.geekhalo.tinyurl.domain.TinyUrlCommandRepository;
import com.geekhalo.tinyurl.domain.TinyUrlQueryRepository;
import com.lego.yxl.core.async.annotation.AsyncForOrderedBasedRocketMQ;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomTinyUrlCommandApplicationServiceImpl implements CustomTinyUrlCommandApplicationService {
    @Autowired
    private TinyUrlCommandRepository tinyUrlCommandRepository;
    @Autowired
    private TinyUrlQueryRepository tinyUrlQueryRepository;

    @Override
    public void incrAccessCount(IncrAccessCountCommand command) {
        incrAccessCountForCache(command);
        Object o = AopContext.currentProxy();
        if (o instanceof CustomTinyUrlCommandApplicationServiceImpl) {
            ((CustomTinyUrlCommandApplicationServiceImpl) o).incrAccessCountForDB(command);
        } else {
            incrAccessCountForDB(command);
        }
    }

    @AsyncForOrderedBasedRocketMQ(
            enable = "${tinyurl.access.incr.async.enable}",
            topic = "${tinyurl.access.incr.async.topic}",
            tag = "IncrAccessCountForDB",
            shardingKey = "#command.id",
            consumerGroup = "${tinyurl.access.incr.consumerGroup}")
    public void incrAccessCountForDB(IncrAccessCountCommand command) {
        this.tinyUrlCommandRepository.incrAccessCount(command.getId(), command.getIncrCount());
    }

    public void incrAccessCountForCache(IncrAccessCountCommand command) {
        this.tinyUrlQueryRepository.incrAccessCount(command.getId(), command.getIncrCount());
    }
}
