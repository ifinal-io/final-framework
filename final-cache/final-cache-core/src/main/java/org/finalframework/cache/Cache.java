package org.finalframework.cache;

import org.finalframework.cache.handler.CacheLockOperationHandler;
import org.finalframework.spring.aop.Executor;
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
 * @see RedisCache
 * @since 1.0
 */
public interface Cache extends Executor {

    /**
     * 获取缓存锁
     *
     * @param key      key 锁的Key
     * @param value    value 锁的Value
     * @param ttl      有效时间，最好不要为 {@linkplain null}，以避免死锁。
     * @param timeUnit 有效时间单位
     * @return 如果获取成功，则返回 {@linkplain true}，否则返回 {@linkplain false}。
     * @see org.finalframework.cache.annotation.CacheLock
     * @see CacheLockOperationHandler
     */
    boolean lock(@NonNull Object key, @Nullable Object value, @Nullable Long ttl, @NonNull TimeUnit timeUnit);

    /**
     * 释放缓存锁，当且仅当缓存 {@code key} 和{@code value}
     *
     * @param key   缓存锁的Key
     * @param value 缓存锁的Value
     * @return 如果缓存释放成功，则返回 {@linkplain true}，否则返回 {@linkplain false}。
     * @see org.finalframework.cache.annotation.CacheLock
     * @see CacheLockOperationHandler
     */
    boolean unlock(@NonNull Object key, @Nullable Object value);

    boolean isExists(@NonNull Object key, @Nullable Object field);

    /**
     * 设置过期时间
     *
     * @param key      缓存key
     * @param ttl      过期时间
     * @param timeUnit 时间单位
     */
    boolean expire(@NonNull Object key, long ttl, @NonNull TimeUnit timeUnit);

    /**
     * 设置缓存
     *
     * @param key      缓存的Key
     * @param field    缓存的 Field
     * @param value    缓存的 Value
     * @param ttl      缓存的有效时间
     * @param timeUnit 时间单位
     * @param view     缓存视图
     * @see org.finalframework.cache.annotation.Cacheable
     * @see org.finalframework.cache.annotation.CachePut
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
     * @see org.finalframework.cache.annotation.Cacheable
     */
    @Nullable
    <T> T get(@NonNull Object key, @Nullable Object field, @NonNull Type type, @Nullable Class<?> view);

    /**
     * 缓存自增
     *
     * @param key   缓存的 key
     * @param field 缓存的 field
     * @param value 增量
     * @return 缓存自增后的值
     * @see org.finalframework.cache.annotation.CacheIncrement
     */
    Long increment(@NonNull Object key, @Nullable Object field, @NonNull Long value);

    /**
     * 缓存自增
     *
     * @param key   缓存的 key
     * @param field 缓存的 field
     * @param value 增量
     * @return 缓存自增后的值
     * @see org.finalframework.cache.annotation.CacheIncrement
     */
    Double increment(@NonNull Object key, @Nullable Object field, @NonNull Double value);

    /**
     * 删除缓存
     *
     * @param key   缓存的 Key
     * @param field 缓存的 Field
     * @return 是否成功
     * @see org.finalframework.cache.annotation.CacheDel
     */
    Boolean del(@NonNull Object key, @Nullable Object field);

}
