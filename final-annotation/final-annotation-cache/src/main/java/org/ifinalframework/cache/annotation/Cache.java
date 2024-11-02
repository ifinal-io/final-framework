/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ifinalframework.cache.annotation;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * 缓存器，定义统一的缓存接口方法。
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Cache {

    /**
     * 获取缓存锁,当且仅当缓存 {@code key} 不存在时，才可猎取成功。
     * <a href="https://final.ilikly.com/cache/annotation/cachelock">CacheLock</a>
     *
     * @param key      key 锁的Key
     * @param value    value 锁的Value
     * @param ttl      有效时间，最好不要为 {@code null}，以避免死锁。
     * @param timeUnit 有效时间单位
     * @return 如果获取成功，则返回 {@code true}，否则返回 {@code false}。
     * @see CacheLock
     * @see #unlock(Object, Object)
     */
    boolean lock(@NonNull Object key, @NonNull Object value, @Nullable Long ttl, @NonNull TimeUnit timeUnit);

    /**
     * 释放缓存锁，当且仅当缓存 {@code key} 和 {@code value}。
     * <a href="https://final.ilikly.com/cache/annotation/cachelock">CacheLock</a>
     *
     * @param key   缓存锁的Key
     * @param value 缓存锁的Value
     * @return 如果缓存释放成功，则返回 {@code true}，否则返回 {@code false}。
     * @see CacheLock
     * @see #lock(Object, Object, Long, TimeUnit)
     */
    boolean unlock(@NonNull Object key, @NonNull Object value);

    /**
     * 检查指定的 {@code key} 和 {@code field} 是否存在
     *
     * <a href="http://doc.redisfans.com/key/exists.html">EXISTS</a>
     * <a href="http://doc.redisfans.com/hash/hexists.html">HEXISTS</a>
     *
     * @param key   缓存 key
     * @param field 缓存域
     * @return 如果存在，返回 {@code true}，否则返回 {@code false}。
     */
    boolean isExists(@NonNull Object key, @Nullable Object field);

    /**
     * 为给定的 {@code key} 设置我正在时间，当 {@code key} 过期时（生存时间为0），它会被自动删除
     *
     * <a href="http://doc.redisfans.com/key/expire.html">EXPIRE</a>
     *
     * @param key      缓存key
     * @param ttl      过期时间
     * @param timeUnit 时间单位
     * @return result
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
     * @see Cacheable
     * @see CachePut
     */
    void set(@NonNull Object key, @Nullable Object field, @Nullable Object value, @Nullable Long ttl,
        @NonNull TimeUnit timeUnit, @Nullable Class<?> view);

    /**
     * 获取缓存
     *
     * @param key   缓存的 Key
     * @param field 缓存的 Field
     * @param type  缓存的数据类型
     * @param view  缓存视图
     * @param <T>   数据类型
     * @return 获取缓存数据，如果不存在，则返回 {@code null}。
     * @see Cacheable
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
     * @see CacheIncrement
     */
    Long increment(@NonNull Object key, @Nullable Object field, @NonNull Long value);

    /**
     * 缓存自增
     *
     * @param key   缓存的 key
     * @param field 缓存的 field
     * @param value 增量
     * @return 缓存自增后的值
     * @see CacheIncrement
     */
    Double increment(@NonNull Object key, @Nullable Object field, @NonNull Double value);

    /**
     * 删除缓存
     *
     * <a href="http://doc.redisfans.com/key/del.html">DEL</a>
     * <a href="http://doc.redisfans.com/hash/hdel.html">HDEL</a>
     *
     * @param key   缓存的 Key
     * @param field 缓存的 Field
     * @return 是否成功
     * @see CacheDel
     */
    Boolean del(@NonNull Object key, @Nullable Object field);

}
