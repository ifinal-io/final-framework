package org.ifinal.finalframework.redis;


import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public final class RedisRegistry {
    private static final RedisRegistry instance = new RedisRegistry();
    private RedisTemplate redisTemplate;

    private RedisRegistry() {
    }

    public static RedisRegistry getInstance() {
        return instance;
    }

    public void setRedisTemplate(final RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate template() {
        return redisTemplate;
    }

    public RedisOperations key() {
        return redisTemplate;
    }

    public ValueOperations value() {
        return redisTemplate.opsForValue();
    }

    public HashOperations hash() {
        return redisTemplate.opsForHash();
    }

    public ListOperations list() {
        return redisTemplate.opsForList();
    }

    public SetOperations set() {
        return redisTemplate.opsForSet();
    }

    public ZSetOperations zSet() {
        return redisTemplate.opsForZSet();
    }


}
