package cn.com.likly.finalframework.redis;

import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:52:35
 * @since 1.0
 */
public interface Redis {

    static void set(@NonNull String key, Object value) {
        RedisRegistry.getInstance().getRedisService().set(key, value);
    }

    static void hset(@NonNull String key, @NonNull String field, Object value) {
        RedisRegistry.getInstance().getRedisService().hset(key, field, value);
    }


}
