package com.ilikly.finalframework.cache;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:50:33
 * @since 1.0
 */
public interface Cache {

    boolean lock(Object key, Object value, Long ttl, TimeUnit timeUnit);

    boolean unlock(Object key, Object value);

    void set(@NonNull Object key, @Nullable Object field, @Nullable Object value, @Nullable Long ttl, @NonNull TimeUnit timeUnit, @Nullable Class<?> view);

    <T> T get(@NonNull Object key, @Nullable Object field, @NonNull Type type, @Nullable Class<?> view);

    Boolean del(@NonNull Object key, @Nullable Object field);

}
