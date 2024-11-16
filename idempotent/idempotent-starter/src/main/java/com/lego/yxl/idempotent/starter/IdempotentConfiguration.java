package com.lego.yxl.idempotent.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lego.yxl.idempotent.core.core.IdempotentExecutorFactory;
import com.lego.yxl.idempotent.core.core.support.ExecutionRecord;
import com.lego.yxl.idempotent.core.core.support.ExecutionRecordRepository;
import com.lego.yxl.idempotent.core.core.support.repository.JpaBasedExecutionRecordRepository;
import com.lego.yxl.idempotent.core.core.support.repository.RedisBasedExecutionRecordRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Configuration
public class IdempotentConfiguration extends IdempotentConfigurationSupport {

    @Bean("dbExecutorFactory")
    public IdempotentExecutorFactory dbExecutorFactory(JpaBasedExecutionRecordRepository recordRepository) {
        return createExecutorFactory(recordRepository);
    }

    @Bean("redisExecutorFactory")
    @ConditionalOnBean(ExecutionRecordRepository.class)
    public IdempotentExecutorFactory redisExecutorFactory(ExecutionRecordRepository executionRecordRepository) {
        return createExecutorFactory(executionRecordRepository);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public ExecutionRecordRepository executionRecordRepository(RedisTemplate<String, ExecutionRecord> recordRedisTemplate) {
        return new RedisBasedExecutionRecordRepository("ide-%s-%s", Duration.ofDays(7), recordRedisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, ExecutionRecord> recordRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ExecutionRecord> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jackson2JsonRedisSerializer<ExecutionRecord> serializer
                = new Jackson2JsonRedisSerializer<>(objectMapper, ExecutionRecord.class);
        redisTemplate.setValueSerializer(serializer);
        return redisTemplate;
    }


}
