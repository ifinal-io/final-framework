package cn.com.likly.finalframework.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 10:00
 * @since 1.0
 */
public interface Cache {

    void set(String key, Object value);

    void set(String key, Object value, long expired, TimeUnit timeUnit);

    <T> T get(String key, Class<T> cacheType);

    void del(String key);

    void hset(String key, String field, Object value);

    void hset(String key, String field, Object value, long expired, TimeUnit timeUnit);

    <T> T hget(String key, String field, Class<T> cacheType);

    void hdel(String key, String field);

}
