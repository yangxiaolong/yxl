package com.lego.yxl.core.support;

public interface ExecutionRecordRepository {
    void update(ExecutionRecord executionRecord);

    ExecutionRecord getOrCreate(int type, String key);
}
