package cn.com.likly.finalframework.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 13:48:35
 * @since 1.0
 */
public interface RedisService {
    void set(Object key, Object value);

    void set(Object key, Object value, long ttl, TimeUnit timeUnit);

    <T> T get(Object key);

    long del(Object... keys);

    void hset(Object key, Object field, Object value);

    <T> T hget(Object key, Object field);

    Map<Object, Object> hgetAll(Object key);

    boolean expire(Object key, long ttl, TimeUnit timeUnit);

    long hdel(Object key, Object... fields);

}
