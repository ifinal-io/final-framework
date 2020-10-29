package org.finalframework.cache;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.json.Json;
import org.finalframework.redis.Redis;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    public boolean lock(@NonNull Object key, @NonNull Object value, @Nullable Long ttl, @Nullable TimeUnit timeUnit) {
        return Redis.lock(key, value, ttl, timeUnit);
    }

    @Override
    public boolean unlock(@NonNull Object key, @NonNull Object value) {
        return Redis.unlock(key, value);
    }

    @Override
    public boolean isExists(@NonNull Object key, @Nullable Object field) {
        return Boolean.TRUE.equals(field == null ? Redis.key().hasKey(key) : Redis.hash().hasKey(key, field));
    }

    @Override
    public boolean expire(@NonNull Object key, long ttl, @NonNull TimeUnit timeUnit) {
        return Boolean.TRUE.equals(Redis.key().expire(key, ttl, timeUnit));
    }

    @Override
    public void set(@NonNull Object key, @Nullable Object field, @Nullable Object value, @Nullable Long ttl, @Nullable TimeUnit timeUnit, @Nullable Class<?> view) {
        final Object cacheValue = view == null ? Json.toJson(value) : Json.toJson(value, view);
        if (field == null) {
            if (ttl != null && ttl > 0 && timeUnit != null) {
                Redis.value().set(key, cacheValue, ttl, timeUnit);
            } else {
                Redis.value().set(key, cacheValue);
            }
        } else {
            Redis.hash().put(key, field, cacheValue);
            if (ttl != null && ttl > 0 && timeUnit != null) {
                Redis.key().expire(key, ttl, timeUnit);
            }
        }
    }

    @Override
    public <T> T get(@NonNull Object key, @Nullable Object field, @NonNull Type type, @Nullable Class<?> view) {
        final Object cacheValue = field == null ? Redis.value().get(key) : Redis.hash().get(key, field);
        if (cacheValue == null) return null;
        final String json = cacheValue.toString();
        return view == null ? Json.toObject(json, type) : Json.toObject(json, type, view);
    }

    @Override
    public Long increment(@NonNull Object key, @Nullable Object field, @NonNull Long value) {
        return field == null ? Redis.value().increment(key, value) : Redis.hash().increment(key, field, value);
    }

    @Override
    public Double increment(@NonNull Object key, @Nullable Object field, @NonNull Double value) {
        return field == null ? Redis.value().increment(key, value) : Redis.hash().increment(key, field, value);
    }

    @Override
    public Boolean del(@NonNull Object key, @Nullable Object field) {
        return field == null ? Boolean.TRUE.equals(Redis.key().delete(key)) : ONE.equals(Redis.hash().delete(key, field));
    }

}
