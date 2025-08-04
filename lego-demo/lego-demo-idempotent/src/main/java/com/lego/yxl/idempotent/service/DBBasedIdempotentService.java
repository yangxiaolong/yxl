package com.lego.yxl.idempotent.service;

import com.lego.yxl.core.idempotent.annotation.Idempotent;
import com.lego.yxl.core.idempotent.annotation.IdempotentHandleType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DBBasedIdempotentService extends BaseIdempotentService {

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Transactional
    public Long putForResult(String key, Long data) {
        return put(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    @Transactional
    public Long putForError(String key, Long data) {
        return put(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Transactional
    public Long putExceptionForResult(String key, Long data) {
        return putException(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    @Transactional
    public Long putExceptionForError(String key, Long data) {
        return putException(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Transactional
    public Long putWaitForResult(String key, Long data) {
        return putForWait(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    @Transactional
    public Long putWaitForError(String key, Long data) {
        return putForWait(key, data);
    }

}
