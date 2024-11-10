package com.lego.idempotent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lego.idempotent.core.IdempotentExecutorFactory;
import com.lego.idempotent.core.support.ExecutionRecord;
import com.lego.idempotent.core.support.ExecutionRecordRepository;
import com.lego.idempotent.core.support.repository.JpaBasedExecutionRecordRepository;
import com.lego.idempotent.core.support.repository.RedisBasedExecutionRecordRepository;
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
    public IdempotentExecutorFactory redisExecutorFactory(ExecutionRecordRepository executionRecordRepository) {
        return createExecutorFactory(executionRecordRepository);
    }

    @Bean
    public ExecutionRecordRepository executionRecordRepository(RedisTemplate<String, ExecutionRecord> recordRedisTemplate) {
        return new RedisBasedExecutionRecordRepository("ide-%s-%s", Duration.ofDays(7), recordRedisTemplate);
    }

    @Bean
    public RedisTemplate<String, ExecutionRecord> recordRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ExecutionRecord> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jackson2JsonRedisSerializer<ExecutionRecord> executionRecordJackson2JsonRedisSerializer
                = new Jackson2JsonRedisSerializer<>(ExecutionRecord.class);
        executionRecordJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(executionRecordJackson2JsonRedisSerializer);
        return redisTemplate;
    }


}
