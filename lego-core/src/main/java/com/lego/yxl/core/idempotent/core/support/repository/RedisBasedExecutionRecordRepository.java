package com.lego.yxl.core.idempotent.core.support.repository;

import com.lego.yxl.core.idempotent.core.support.ExecutionRecordRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RedisBasedExecutionRecordRepository implements ExecutionRecordRepository {

    private final String keyTemplate;
    private final Duration duration;
    private final RedisTemplate<String, ExecutionRecord> recordRedisTemplate;

    public RedisBasedExecutionRecordRepository(String keyTemplate,
                                               Duration duration,
                                               RedisTemplate<String, ExecutionRecord> recordRedisTemplate) {
        this.keyTemplate = keyTemplate;
        this.duration = duration;
        this.recordRedisTemplate = recordRedisTemplate;
    }

    @Override
    public void update(ExecutionRecord executionRecord) {
        String cacheKey = createCacheKey(executionRecord.getType(), executionRecord.getUniqueKey());
        this.recordRedisTemplate.opsForValue().set(cacheKey, executionRecord, this.duration);
    }

    @Override
    public ExecutionRecord getOrCreate(int type, String key) {
        String cacheKey = createCacheKey(type, key);
        ExecutionRecord executionRecord = ExecutionRecord.apply(type, key);
        executionRecord.init();
        boolean result = Boolean.TRUE.equals(this.recordRedisTemplate.opsForValue()
                .setIfAbsent(cacheKey, executionRecord, this.duration));
        // 设置成功，直接返回
        if (result) {
            return executionRecord;
        }

        // 已经有记录，如果是 none 状态，说明在处理中
        ExecutionRecord record = this.recordRedisTemplate.opsForValue().get(cacheKey);
        if (record.isNone()) {
            record.ing();
        }
        return record;
    }

    private String createCacheKey(int group, String key) {
        return String.format(this.keyTemplate, group, key);
    }
}
