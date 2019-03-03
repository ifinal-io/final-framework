package com.ilikly.finalframework.cache;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.json.Json;
import com.ilikly.finalframework.redis.Redis;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
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
    public void set(Object key, Object value, long ttl, TimeUnit timeUnit, Class<?> view) {
        if (ttl > 0) {
            Redis.value().set(key, view == null ? Json.toJson(value) : Json.toJson(value, view), ttl, timeUnit);
        } else {
            Redis.value().set(key, view == null ? Json.toJson(value) : Json.toJson(value, view));
        }
    }

    @Override
    public <T> T get(Object key, Type type, Class<?> view) {
        final String value = (String) Redis.value().get(key);
        return Assert.isEmpty(value) ? null : view == null ? Json.parse(value, type) : Json.parse(value, type, view);
    }


    @Override
    public Boolean del(Object key) {
        return Redis.key().delete(key);
    }

    @Override
    public void hset(Object key, Object field, Object value, long ttl, TimeUnit timeUnit, Class<?> view) {
        String json = view == null ? Json.toJson(value) : Json.toJson(value, view);
        Redis.hash().put(key, field, json);
        if (ttl > 0) {
            Redis.key().expire(key, ttl, timeUnit);
        }
    }

    @Override
    public <T> T hget(Object key, Object field, Type type, Class<?> view) {
        final String value = (String) Redis.hash().get(key, field);
        return Assert.isEmpty(value) ? null : view == null ? Json.parse(value, type) : Json.parse(value, type, view);
    }

    @Override
    public Long hdel(Object key, Object... field) {
        return Redis.hash().delete(key, field);
    }


}
