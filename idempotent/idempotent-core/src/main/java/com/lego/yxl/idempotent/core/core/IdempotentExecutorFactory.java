package com.lego.yxl.idempotent.core.core;

public interface IdempotentExecutorFactory {
    IdempotentExecutor create(IdempotentMeta meta);
}
