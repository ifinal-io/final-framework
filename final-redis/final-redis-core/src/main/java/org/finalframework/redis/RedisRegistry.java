package org.finalframework.redis;


import org.springframework.data.redis.core.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:53:01
 * @since 1.0
 */
public final class RedisRegistry {
    private static final RedisRegistry instance = new RedisRegistry();
    private RedisTemplate redisTemplate;

    private RedisRegistry() {
    }

    public static RedisRegistry getInstance() {
        return instance;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<?, ?> template() {
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

    public ZSetOperations zset() {
        return redisTemplate.opsForZSet();
    }


}
