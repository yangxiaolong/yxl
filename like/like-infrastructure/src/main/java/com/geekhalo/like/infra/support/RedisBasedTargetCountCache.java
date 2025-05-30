package com.geekhalo.like.infra.support;

import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;
import com.google.common.collect.Lists;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class RedisBasedTargetCountCache<C extends AbstractTargetCount>
    implements TargetCountCache<C>{
    private static String LUA_SCRIPT =
                    "if redis.call('exists',KEYS[1]) == 0 " +
                        "then return 0 " +
                    "else " +
                        "return redis.call('incrby',KEYS[1],ARGV[1]) " +
                    "end";

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    protected abstract String cacheKeyTemplate();

    protected abstract List<C> loadByTargetTypeAndTargetIdIn(String type, List<Long> ids);

    protected abstract C buildResult(String type, Long targetId, Long count);

    @Override
    public void incr(ActionTarget target, long count) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);
        List<String> keys = Arrays.asList(createCacheKey(target));
        this.redisTemplate.execute(redisScript, keys, count);
    }

    @Override
    public void sync(C entity) {
        String key = createCacheKey(entity.getTarget());
        this.redisTemplate.delete(key);
    }

    @Override
    public List<C> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds) {

        List<String> keys = targetIds.stream()
                .map(targetId -> createCacheKey(type, targetId))
                .collect(Collectors.toList());

        List<Long> countFromCache = this.redisTemplate.opsForValue().multiGet(keys);
        List<ValueWrapper> valueWrappers = toValueWrapper(targetIds, countFromCache);

        List<C> valueFromCache = valueWrappers.stream()
                .filter(valueWrapper -> valueWrapper.getCount() != null)
                .map(valueWrapper -> buildResult(type, valueWrapper.targetId, valueWrapper.getCount()))
                .collect(Collectors.toList());

        List<Long> missTargetIds = new ArrayList<>(targetIds);
        for (ValueWrapper count : valueWrappers){
            if (count.getCount() != null) {
                Long targetId = count.getTargetId();
                missTargetIds.remove(targetId);
            }
        }

        if (CollectionUtils.isEmpty(missTargetIds)){
            log.info("load All Data From Cache for {} and {}", type, targetIds);
            return valueFromCache;
        }

        List<C> valueFromDB = this.loadByTargetTypeAndTargetIdIn(type, missTargetIds);
        if (CollectionUtils.isNotEmpty(valueFromDB)) {
            Map<String, Long> data = valueFromDB.stream()
                    .collect(Collectors.toMap(count -> createCacheKey(count.getTarget()), t -> t.getCount()));
            this.redisTemplate.opsForValue().multiSet(data);

        }

        List<C> result = Lists.newArrayListWithCapacity(countFromCache.size() + valueFromDB.size());
        result.addAll(valueFromCache);
        result.addAll(valueFromDB);
        return  result;
    }


    private String createCacheKey(ActionTarget actionTarget){
        return createCacheKey(actionTarget.getType(), actionTarget.getId());
    }

    private String createCacheKey(String type, Long id) {
        String key = this.cacheKeyTemplate();
        key = key.replace("{type}", type);
        key = key.replace("{id}", String.valueOf(id));
        return  key;
    }

    private List<ValueWrapper> toValueWrapper(List<Long> targetId, List<Long> count){
        List<ValueWrapper> result = Lists.newArrayListWithCapacity(targetId.size());
        for (int i = 0; i< targetId.size() ; i++){
            result.add(new ValueWrapper(targetId.get(i), count.get(i)));
        }
        return result;
    }

    @Value
    class ValueWrapper{
        private final Long targetId;
        private final Long count;
    }
}
