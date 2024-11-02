/*
 * Copyright 2020-2021 the original author or authors.
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
 */

package org.ifinalframework.cache.annotation;

import org.ifinalframework.core.lang.SpEL;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存锁，在方法执行之前尝试获取 {@link #key} 并且值为 {@link #value()} 所表示的锁，并设置锁的过期时间为 {@link #ttl()},单位为 {@link #timeunit()}。
 * 有重试机制，重试次数为 {@link #retry()}，间隔 {@link #sleep()},单位为 {@link TimeUnit#MILLISECONDS}.
 *
 * @author iimik
 * @see Cache#lock(Object, Object, Long, TimeUnit)
 * @see Cache#unlock(Object, Object)
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheLock {

    /**
     * 缓存锁 key
     *
     * @return key
     */
    @SpEL
    @NonNull
    String[] key();

    /**
     * 缓存锁 value
     *
     * @return value
     */
    @SpEL
    @Nullable
    String value() default "";

    @NonNull
    String delimiter() default ":";

    @SpEL
    @Nullable
    String condition() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    int order() default Ordered.HIGHEST_PRECEDENCE;

    /**
     * 重试次数
     *
     * @return retry
     */
    int retry() default 1;

    /**
     * 重试间隔，单位 {@link TimeUnit#MILLISECONDS}
     *
     * @return sleep
     */
    long sleep() default 1000;

}
