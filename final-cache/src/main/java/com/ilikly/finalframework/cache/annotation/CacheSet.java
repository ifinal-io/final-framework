package com.ilikly.finalframework.cache.annotation;

import com.ilikly.finalframework.cache.Cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @since 1.0
 * @see Cache#set(Object, Object, long, TimeUnit)
 * @see Cache#hset(Object, Object, Object, long, TimeUnit)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheSet {
    String key();

    String field() default "";

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
