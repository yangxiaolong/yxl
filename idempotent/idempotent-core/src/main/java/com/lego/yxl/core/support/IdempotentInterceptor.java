package com.lego.yxl.core.support;

import com.google.common.collect.Maps;
import com.lego.yxl.core.IdempotentExecutor;
import com.lego.yxl.core.IdempotentMeta;
import com.lego.yxl.core.IdempotentMetaParser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;

public class IdempotentInterceptor implements MethodInterceptor {

    private final Map<Method, IdempotentExecutor> cache = Maps.newConcurrentMap();
    private final IdempotentMetaParser parser;
    private final IdempotentExecutorFactories factories;

    public IdempotentInterceptor(IdempotentMetaParser parser, IdempotentExecutorFactories factories) {
        this.parser = parser;
        this.factories = factories;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        IdempotentExecutor executor = this.cache.computeIfAbsent(invocation.getMethod(), this::createExecutor);
        if (executor != null) {
            return executor.invoke(invocation);
        }
        return invocation.proceed();
    }

    private IdempotentExecutor createExecutor(Method method) {
        IdempotentMeta meta = this.parser.parse(method);
        return factories.create(meta);
    }
}
