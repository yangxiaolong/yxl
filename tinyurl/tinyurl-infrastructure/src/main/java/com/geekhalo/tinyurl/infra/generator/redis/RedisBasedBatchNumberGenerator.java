package com.geekhalo.tinyurl.infra.generator.redis;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.generator.AbstractBatchNumberGenerator;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

public class RedisBasedBatchNumberGenerator
        extends AbstractBatchNumberGenerator
        implements NumberGenerator {

    @Autowired
    @Getter(AccessLevel.PRIVATE)
    private StringRedisTemplate stringRedisTemplate;

    @Value("${tinyurl.number.generator.redis.key:number-generator}")
    @Getter(AccessLevel.PRIVATE)
    private String generatorKey;

    @Value("${tinyurl.number.generator.batchSize:500}")
    private int batchSize = 500;

    @Override
    protected List<Long> batchLoad() {
        long end = this.getStringRedisTemplate().opsForValue()
                .increment(getGeneratorKey(), batchSize);
        long start = end - batchSize + 1;
        List<Long> numbers = Lists.newArrayListWithCapacity(batchSize);
        // 批量生成 Number
        for (int i = 0; i< batchSize; i++){
            numbers.add(start + i);
        }
        return numbers;
    }
}
