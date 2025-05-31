package com.geekhalo.tinyurl.infra.config;

import com.geekhalo.tinyurl.infra.repository.cache.NullTinyUrlCache;
import com.geekhalo.tinyurl.infra.repository.cache.RedisBasedTinyUrlCache;
import com.geekhalo.tinyurl.infra.repository.cache.TinyUrlCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration("tinyurlCacheConfiguration")
public class CacheConfiguration {
    @Value("${tinyurl.cache.enable:false}")
    private boolean cacheEnable;

    @Configuration
    @ConditionalOnClass(RedisOperations.class)
    static class RedisTemplateConfiguration{
        @Bean
        @ConditionalOnMissingBean
        public RedisTemplate<String, Long> tinyurlRedisTemplate(RedisConnectionFactory redisConnectionFactory){
            RedisTemplate<String, Long> redisTemplate = new RedisTemplate();
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            return redisTemplate;
        }
    }

    @Bean
    public TinyUrlCache tinyUrlCache(){
        if (this.cacheEnable){
            return new RedisBasedTinyUrlCache();
        }else {
            return new NullTinyUrlCache();
        }
    }
}
