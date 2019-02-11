package com.ilikly.finalframework.cache;

import com.ilikly.finalframework.redis.Redis;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 23:56:03
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class RedisCache implements Cache {

    @PostConstruct
    public void init() {
        CacheRegistry.getInstance().registerCache(null, this);
    }

    @Override
    public void set(Object key, Object value, long ttl, TimeUnit timeUnit) {
        if (ttl > 0) {
            Redis.value().set(key, value, ttl, timeUnit);
        } else {
            Redis.value().set(key, value);
        }
    }

    @Override
    public <T> T get(Object key) {
        return (T) Redis.value().get(key);
    }

    @Override
    public Boolean del(Object key) {
        return Redis.key().delete(key);
    }

    @Override
    public void hset(Object key, Object field, Object value, long ttl, TimeUnit timeUnit) {
        Redis.hash().put(key, field, value);
        if (ttl > 0) {
            Redis.key().expire(key, ttl, timeUnit);
        }
    }

    @Override
    public <T> T hget(Object key, Object field) {
        return (T) Redis.hash().get(key, field);
    }

    @Override
    public Long hdel(Object key, Object... field) {
        return Redis.hash().delete(key, field);
    }


}
