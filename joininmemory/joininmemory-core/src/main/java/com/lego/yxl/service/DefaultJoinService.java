package com.lego.yxl.service;

import com.google.common.collect.Maps;
import com.lego.yxl.executor.items.JoinItemsExecutor;
import com.lego.yxl.factory.items.JoinItemsExecutorFactory;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Join 服务对外接口
 */
public class DefaultJoinService implements JoinService {
    private final JoinItemsExecutorFactory joinItemsExecutorFactory;

    /**
     * 缓存，避免频繁的初始化
     */
    private final Map<Class, JoinItemsExecutor> cache = Maps.newConcurrentMap();

    public DefaultJoinService(JoinItemsExecutorFactory joinItemsExecutorFactory) {
        this.joinItemsExecutorFactory = joinItemsExecutorFactory;
    }

    @Override
    public <T> void joinInMemory(Class<T> tCls, List<T> t) {
        JoinItemsExecutor<T> joinItemsExecutor = this.cache.computeIfAbsent(tCls, this::createJoinExecutorGroup);
        joinItemsExecutor.execute(t);
    }

    @Override
    public <T> void register(Class<T> tCls) {
        this.cache.computeIfAbsent(tCls, this::createJoinExecutorGroup);
    }

    private <T> JoinItemsExecutor<T> createJoinExecutorGroup(Class<T> aClass) {
        return this.joinItemsExecutorFactory.createFor(aClass);
    }

}
