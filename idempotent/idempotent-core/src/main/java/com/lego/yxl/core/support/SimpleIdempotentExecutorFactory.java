package com.lego.yxl.core.support;

import com.lego.yxl.core.IdempotentExecutor;
import com.lego.yxl.core.IdempotentExecutorFactory;
import com.lego.yxl.core.IdempotentMeta;
import lombok.Setter;

@Setter
public class SimpleIdempotentExecutorFactory implements IdempotentExecutorFactory {
    private IdempotentKeyParser idempotentKeyParser;
    private ExecutionResultSerializer serializer;
    private ExecutionRecordRepository executionRecordRepository;

    @Override
    public IdempotentExecutor create(IdempotentMeta meta) {
        return new SimpleIdempotentExecutor(meta,
                idempotentKeyParser,
                this.serializer,
                this.executionRecordRepository);
    }
}
