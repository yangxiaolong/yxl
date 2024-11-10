package com.lego.yxl.idempotent.demo.service;

import com.lego.yxl.idempotent.core.annotation.Idempotent;
import com.lego.yxl.idempotent.core.annotation.IdempotentHandleType;
import org.springframework.stereotype.Service;

@Service
public class RedisBasedIdempotentService extends BaseIdempotentService {

    @Idempotent(executorFactory = "redisExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Override
    public Long putForResult(String key, Long data) {
        return put(key, data);
    }

    @Idempotent(executorFactory = "redisExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    @Override
    public Long putForError(String key, Long data) {
        return put(key, data);
    }

    @Idempotent(executorFactory = "redisExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Override
    public Long putExceptionForResult(String key, Long data) {
        return putException(key, data);
    }

    @Idempotent(executorFactory = "redisExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    @Override
    public Long putExceptionForError(String key, Long data) {
        return putException(key, data);
    }

    @Override
    @Idempotent(executorFactory = "redisExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    public Long putWaitForResult(String key, Long data) {
        return putForWait(key, data);
    }

    @Override
    @Idempotent(executorFactory = "redisExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    public Long putWaitForError(String key, Long data) {
        return putForWait(key, data);
    }

}
