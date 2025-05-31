package com.geekhalo.tinyurl.infra.generator.redis;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisBasedSingleNumberGenerator
        implements NumberGenerator {
    @Autowired
    @Getter(AccessLevel.PRIVATE)
    private StringRedisTemplate stringRedisTemplate;

    @Value("${tinyurl.number.generator.redis.key:number-generator}")
    @Getter(AccessLevel.PRIVATE)
    private String generatorKey;
    
    @Override
    public Long nextNumber() {
        return this.getStringRedisTemplate().opsForValue().increment(this.getGeneratorKey());
    }
}
