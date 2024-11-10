package com.lego.yxl.idempotent.core.core.support;

import com.lego.yxl.idempotent.core.core.IdempotentExecutor;
import com.lego.yxl.idempotent.core.core.IdempotentExecutorFactory;
import com.lego.yxl.idempotent.core.core.IdempotentMeta;
import lombok.Setter;

@Setter
public class SimpleIdempotentExecutorFactory implements IdempotentExecutorFactory {
    private IdempotentKeyParser idempotentKeyParser;
    private ExecutionResultSerializer serializer;
    private ExecutionRecordRepository executionRecordRepository;

    @Override
    public IdempotentExecutor create(IdempotentMeta meta) {
        return new SimpleIdempotentExecutor(meta,
                this.idempotentKeyParser,
                this.serializer,
                this.executionRecordRepository);
    }
}
