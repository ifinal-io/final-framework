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

import org.ifinalframework.cache.annotation.CachePut.CachePuts;
import org.ifinalframework.core.aop.JoinPoint;
import org.ifinalframework.core.lang.SpEL;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * CachePut.
 *
 * @author iimik
 * @version 1.0.0
 * @see Cache#set(Object, Object, Object, Long, TimeUnit, Class)
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CachePuts.class)
public @interface CachePut {

    @SpEL
    String[] key();

    @SpEL
    String[] field() default {};

    @SpEL
    String value() default "";

    String delimiter() default ":";

    JoinPoint point() default JoinPoint.AFTER_RETURNING;

    @SpEL
    String condition() default "";

    @SpEL
    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * CachePuts.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface CachePuts {

        CachePut[] value();

    }

}
