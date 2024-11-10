package com.lego.yxl.idempotent.core.core.support;

public interface ExecutionRecordRepository {
    void update(ExecutionRecord executionRecord);

    ExecutionRecord getOrCreate(int type, String key);
}
