package cn.com.likly.finalframework.redis;

import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:48:35
 * @since 1.0
 */
public interface RedisService {
    void set(String key, Object value);

    <T> T get(String key, Class<T> cacheType);

    <T> T get(String key, Type cacheType);

    void hset(String key, String field, Object value);
}
