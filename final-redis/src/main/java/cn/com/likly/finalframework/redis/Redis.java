package cn.com.likly.finalframework.redis;

import lombok.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:52:35
 * @since 1.0
 */
public interface Redis {

    static void set(@NonNull Object key, Object value) {
        RedisRegistry.getInstance().getRedisService().set(key, value);
    }

    static void set(@NonNull Object key, Object value, long ttl, TimeUnit timeUnit) {
        RedisRegistry.getInstance().getRedisService().set(key, value, ttl, timeUnit);
    }

    static <T> T get(@NonNull Object key) {
        return RedisRegistry.getInstance().getRedisService().get(key);
    }

    static long del(Object... keys) {
        return RedisRegistry.getInstance().getRedisService().del(keys);
    }

    static void hset(@NonNull Object key, @NonNull Object field, Object value) {
        RedisRegistry.getInstance().getRedisService().hset(key, field, value);
    }

    static <T> T hget(@NonNull Object key, @NonNull Object field) {
        return RedisRegistry.getInstance().getRedisService().hget(key, field);
    }


    static long hdel(@NonNull Object key, @NonNull Object... fields) {
        return RedisRegistry.getInstance().getRedisService().hdel(key, fields);
    }

    static boolean expire(@NonNull Object key, long ttl, TimeUnit timeUnit) {
        return RedisRegistry.getInstance().getRedisService().expire(key, ttl, timeUnit);
    }

}
