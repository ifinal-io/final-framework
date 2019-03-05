package com.ilikly.finalframework.cache;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:50:33
 * @since 1.0
 */
public interface Cache {

    void set(Object key, Object value, Long ttl, TimeUnit timeUnit, Class<?> view);

    <T> T get(Object key, Type type, Class<?> view);

    Boolean del(Object key);

    void hset(Object key, Object field, Object value, Long ttl, TimeUnit timeUnit, Class<?> view);

    <T> T hget(Object key, Object field, Type type, Class<?> view);

    Long hdel(Object key, Object... field);

}
