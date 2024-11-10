package com.lego.idempotent.core.support;

import com.lego.idempotent.core.IdempotentExecutor;
import com.lego.idempotent.core.IdempotentExecutorFactory;
import com.lego.idempotent.core.IdempotentMeta;
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
