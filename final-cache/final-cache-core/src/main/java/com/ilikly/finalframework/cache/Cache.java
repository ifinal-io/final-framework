package com.ilikly.finalframework.cache;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * 缓存器，定义统一的缓存接口方法。
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:50:33
 * @since 1.0
 */
public interface Cache {

    /**
     * 获取缓存锁
     *
     * @param key      key 锁的Key
     * @param value    value 锁的Value
     * @param ttl      有效时间，最好不要为 {@linkplain null}，以避免死锁。
     * @param timeUnit 有效时间单位
     * @return 如果获取成功，则返回 {@linkplain true}，否则返回 {@linkplain false}。
     */
    boolean lock(@NonNull Object key, @Nullable Object value, @Nullable Long ttl, @NonNull TimeUnit timeUnit);

    /**
     * 释放缓存锁，当且仅当缓存 {@linkplain key} 和{@linkplain}
     *
     * @param key   缓存锁的Key
     * @param value 缓存锁的Value
     * @return 如果缓存释放成功，则返回 {@linkplain true}，否则返回 {@linkplain false}。
     */
    boolean unlock(@NonNull Object key, @Nullable Object value);

    /**
     * 设置缓存
     *
     * @param key      缓存的Key
     * @param field    缓存的 Field
     * @param value    缓存的 Value
     * @param ttl      缓存的有效时间
     * @param timeUnit 时间单位
     * @param view     缓存视图
     */
    void set(@NonNull Object key, @Nullable Object field, @Nullable Object value, @Nullable Long ttl, @NonNull TimeUnit timeUnit, @Nullable Class<?> view);

    /**
     * 获取缓存
     *
     * @param key   缓存的 Key
     * @param field 缓存的 Field
     * @param type  缓存的数据类型
     * @param view  缓存视图
     * @param <T>   数据类型
     * @return 获取缓存数据，如果不存在，则返回 {@linkplain null}。
     */
    @Nullable
    <T> T get(@NonNull Object key, @Nullable Object field, @NonNull Type type, @Nullable Class<?> view);

    /**
     * 删除缓存
     *
     * @param key   缓存的 Key
     * @param field 缓存的 Field
     * @return 是否成功
     */
    Boolean del(@NonNull Object key, @Nullable Object field);

}
