package com.lego.yxl.core;

public interface IdempotentExecutorFactory {
    IdempotentExecutor create(IdempotentMeta meta);
}
