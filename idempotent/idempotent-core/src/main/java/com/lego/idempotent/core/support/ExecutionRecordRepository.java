package com.lego.idempotent.core.support;

public interface ExecutionRecordRepository {
    void update(ExecutionRecord executionRecord);

    ExecutionRecord getOrCreate(int type, String key);
}
