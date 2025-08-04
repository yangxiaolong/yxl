package com.lego.yxl.core.idempotent.core;

public interface IdempotentExecutorFactory {
    IdempotentExecutor create(IdempotentMeta meta);
}
