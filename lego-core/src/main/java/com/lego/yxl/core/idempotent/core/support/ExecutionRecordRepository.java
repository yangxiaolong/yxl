package com.lego.yxl.core.idempotent.core.support;

import com.lego.yxl.core.idempotent.core.support.repository.ExecutionRecord;

public interface ExecutionRecordRepository {
    void update(ExecutionRecord executionRecord);

    ExecutionRecord getOrCreate(int type, String key);
}
