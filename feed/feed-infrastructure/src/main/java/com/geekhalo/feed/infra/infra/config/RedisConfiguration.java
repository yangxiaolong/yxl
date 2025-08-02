package com.geekhalo.feed.infra.infra.config;

import com.geekhalo.feed.domain.feed.FeedIndex;
import com.google.common.base.Charsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, FeedIndex> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, FeedIndex> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer(Charsets.UTF_8));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<FeedIndex>(FeedIndex.class));
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
