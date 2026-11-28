package com.lego.yxl.idempotent.service;

import com.lego.yxl.core.idempotent.annotation.Idempotent;
import com.lego.yxl.core.idempotent.annotation.IdempotentHandleType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DBBasedIdempotentService extends BaseIdempotentService {

    /*
    - `executorFactory`：用哪个执行器工厂。这里指定 `dbExecutorFactory`，意味着执行记录存数据库；
        换成 `redisExecutorFactory` 就存 Redis。
    - `group`：业务分组。不同业务用不同 group，避免 key 撞车。
    - `keyEl`：用 SpEL 从入参里提取幂等键。`#key` 就表示拿第一个参数 `key` 的值。
    - `handleType`：重复提交时怎么办。`RESULT` 返回上次结果，`ERROR` 抛 `RepeatedSubmitException`。
    `group + key` 才是一条完整的幂等键

     */
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
