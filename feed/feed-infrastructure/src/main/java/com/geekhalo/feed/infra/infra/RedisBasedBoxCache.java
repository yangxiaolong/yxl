package com.geekhalo.feed.infra.infra;

import com.geekhalo.feed.domain.box.BoxCache;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class RedisBasedBoxCache implements BoxCache {
    private static final String LUA_SCRIPT =
            "local key = KEYS[1]\n" +
            "local feedIndex = ARGV[1]\n" +
            "local score = tonumber(ARGV[2])\n" +
            "local maxLength = tonumber(ARGV[3])\n" +
            "\n" +
            "redis.call(\"ZADD\", key, score, feedIndex)\n" +
            "local size = redis.call(\"ZCARD\", key)\n" +
            "if size > maxLength then\n" +
            "  local elementsToRemove = size - maxLength\n" +
            "  redis.call(\"ZREMRANGEBYRANK\", key, 0, elementsToRemove)\n" +
            "end";
    @Autowired
    private RedisTemplate<String, FeedIndex> feedIndexRedisTemplate;

    @Value("${box.cache:box-cache-{ownerId}-{ownerType}-{boxType}}")
    private String keyTemplate;

    @Override
    public void append(FeedOwner feedOwner, BoxType boxType, FeedIndex feedIndex) {
        String key = createCachedKey(feedOwner, boxType);
//        this.feedIndexRedisTemplate.opsForZSet()
//                .add(key, feedIndex, feedIndex.getScore());
//        Long size = this.feedIndexRedisTemplate.opsForZSet()
//                .size(key);
//        if (size > boxType.getMaxLength()) {
//            long elementsToRemove = size - boxType.getMaxLength();
//            this.feedIndexRedisTemplate.opsForZSet()
//                    .removeRange(key, 0, elementsToRemove);
//        }

        RedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);
        List<String> keys = Collections.singletonList(key);
        this.feedIndexRedisTemplate.execute(redisScript, keys, feedIndex, feedIndex.getScore(), boxType.getMaxCacheLength());
    }

    private String createCachedKey(FeedOwner feedOwner, BoxType boxType) {
        String key = keyTemplate;
        key = key.replace("{ownerType}", String.valueOf(feedOwner.getOwnerType().getCode()));
        key = key.replace("{ownerId}", String.valueOf(feedOwner.getOwnerId()));
        key = key.replace("{boxType}", String.valueOf(boxType.getCode()));
        return key;
    }

    @Override
    public List<FeedIndex> load(FeedOwner feedOwner, BoxType boxType, Long score, Integer size) {
        String key = createCachedKey(feedOwner, boxType);
        Set<FeedIndex> feedIndices = this.feedIndexRedisTemplate.opsForZSet()
                .reverseRangeByScore(key, Double.NEGATIVE_INFINITY, score - 1, 0, size);
        return Lists.newArrayList(feedIndices);
    }
}
