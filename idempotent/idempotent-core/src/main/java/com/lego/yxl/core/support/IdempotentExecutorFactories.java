package com.lego.yxl.core.support;

import com.google.common.collect.Maps;
import com.lego.yxl.core.IdempotentExecutor;
import com.lego.yxl.core.IdempotentExecutorFactory;
import com.lego.yxl.core.IdempotentMeta;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class IdempotentExecutorFactories {
    private final Map<String, IdempotentExecutorFactory> factoryMap = Maps.newConcurrentMap();

    public IdempotentExecutorFactories(Map<String, IdempotentExecutorFactory> factoryMap) {
        this.factoryMap.putAll(factoryMap);
    }

    public IdempotentExecutor create(IdempotentMeta meta) {
        if (meta == null) {
            return NllIdempotentExecutor.getInstance();
        }

        IdempotentExecutorFactory idempotentExecutorFactory = factoryMap.get(meta.executorFactory());
        if (idempotentExecutorFactory == null) {
            log.error("Failed to find IdempotentExecutorFactory for {}", meta.executorFactory());
            return NllIdempotentExecutor.getInstance();
        }
        return idempotentExecutorFactory.create(meta);
    }
}
