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

import org.ifinalframework.cache.annotation.CacheIncrement.CacheIncrements;
import org.ifinalframework.core.aop.JoinPoint;
import org.ifinalframework.core.lang.SpEL;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author iimik
 * @version 1.0.0
 * @see Cache#increment(Object, Object, Long)
 * @see Cache#increment(Object, Object, Double)
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CacheIncrements.class)
public @interface CacheIncrement {

    /**
     * 缓存区
     *
     * @return key
     */
    @SpEL
    String[] key();

    /**
     * 缓存域
     *
     * @return field
     */
    @SpEL
    String[] field() default {};

    /**
     * 缓存区域的分隔符
     *
     * @return delimiter
     */
    String delimiter() default ":";

    String value() default "{1}";

    Class<? extends Number> type() default int.class;

    /**
     * 缓存条件
     *
     * @return when
     * @see #when()
     */
    @SpEL
    @AliasFor("when")
    String condition() default "";

    /**
     * 缓存条件
     *
     * @return condition
     * @see #condition()
     */
    @SpEL
    @AliasFor("condition")
    String when() default "";

    JoinPoint point() default JoinPoint.AFTER_RETURNING;

    /**
     * 过期时间
     *
     * @return expire
     */
    String expire() default "";

    /**
     * 有效时间
     *
     * @return ttl
     */
    long ttl() default -1L;

    /**
     * 有效时间单位
     *
     * @return timeunit
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * CacheIncrements.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface CacheIncrements {

        CacheIncrement[] value();

    }

}
