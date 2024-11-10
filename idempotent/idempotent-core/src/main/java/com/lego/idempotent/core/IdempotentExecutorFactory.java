package com.lego.idempotent.core;

public interface IdempotentExecutorFactory {
    IdempotentExecutor create(IdempotentMeta meta);
}
