package com.geekhalo.like.infra;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.infra.dislike.DislikeTargetCountCache;
import com.geekhalo.like.infra.dislike.NullDislikeTargetCountCache;
import com.geekhalo.like.infra.like.LikeTargetCountCache;
import com.geekhalo.like.infra.like.NullLikeTargetCountCache;
import com.geekhalo.like.infra.support.TargetCountCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfiguration {
    @Value("${target.count.dislike.cache.enable:false}")
    private boolean dislikeCacheEnable;

    @Value("${target.count.like.cache.enable:false}")
    private boolean likeCacheEnable;

    @Configuration
    @ConditionalOnClass(RedisOperations.class)
    static class RedisTemplateConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public RedisTemplate<String, Long> targetCountRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
            return redisTemplate;
        }
    }

    @Bean
    public TargetCountCache<DislikeTargetCount> dislikeTargetCountTargetCountCache() {
        if (this.dislikeCacheEnable) {
            return new DislikeTargetCountCache();
        } else {
            return new NullDislikeTargetCountCache();
        }
    }

    @Bean
    public TargetCountCache<LikeTargetCount> likeTargetCountTargetCountCache() {
        if (this.likeCacheEnable) {
            return new LikeTargetCountCache();
        } else {
            return new NullLikeTargetCountCache();
        }
    }

}
