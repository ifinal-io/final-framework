

package org.finalframework.cache;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.json.Json;
import org.finalframework.redis.Redis;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 23:56:03
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@Primary
@SpringComponent
public class RedisCache implements Cache {

    private static final Long ONE = 1L;

    @Override
    public boolean lock(Object key, Object value, Long ttl, TimeUnit timeUnit) {
        return Redis.lock(key, value, ttl, timeUnit);
    }

    @Override
    public boolean unlock(Object key, Object value) {
        return Redis.unlock(key, value);
    }

    @Override
    public boolean isExists(Object key, Object field) {
        return Boolean.TRUE.equals(field == null ? Redis.key().hasKey(key) : Redis.hash().hasKey(key, field));
    }

    @Override
    public boolean expire(Object key, long ttl, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(Redis.key().expire(key, ttl, timeUnit));
    }

    @Override
    public void set(Object key, Object field, Object value, Long ttl, TimeUnit timeUnit, Class<?> view) {
        final Object cacheValue = view == null ? Json.toJson(value) : Json.toJson(value, view);
        if (field == null) {
            if (ttl != null && ttl > 0) {
                Redis.value().set(key, cacheValue, ttl, timeUnit);
            } else {
                Redis.value().set(key, cacheValue);
            }
        } else {
            Redis.hash().put(key, field, cacheValue);
            if (ttl != null && ttl > 0) {
                Redis.key().expire(key, ttl, timeUnit);
            }
        }
    }

    @Override
    public <T> T get(Object key, Object field, Type type, Class<?> view) {
        final Object cacheValue = field == null ? Redis.value().get(key) : Redis.hash().get(key, field);
        if (cacheValue == null) return null;
        final String json = cacheValue.toString();
        return view == null ? Json.toObject(json, type) : Json.toObject(json, type, view);
    }

    @Override
    public Long increment(Object key, Object field, Long value) {
        return field == null ? Redis.value().increment(key, value) : Redis.hash().increment(key, field, value);
    }

    @Override
    public Double increment(Object key, Object field, Double value) {
        return field == null ? Redis.value().increment(key, value) : Redis.hash().increment(key, field, value);
    }

    @Override
    public Boolean del(Object key, Object field) {
        return field == null ? Boolean.TRUE.equals(Redis.key().delete(key)) : ONE.equals(Redis.hash().delete(key, field));
    }

}
