package com.ilikly.finalframework.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:50:33
 * @since 1.0
 */
public interface Cache {

    void set(Object key, Object value, long ttl, TimeUnit timeUnit);

    <T> T get(Object key);

    Boolean del(Object key);

    void hset(Object key, Object field, Object value, long ttl, TimeUnit timeUnit);

    <T> T hget(Object key, Object field);

    Long hdel(Object key, Object... field);

}
