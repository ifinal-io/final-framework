package com.ilikly.finalframework.cache.annotation;

import com.ilikly.finalframework.cache.Cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @see Cache#set(Object, Object, long, TimeUnit)
 * @see Cache#hset(Object, Object, Object, long, TimeUnit)
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheSet {

    String keyPattern() default "";

    String[] keys();

    String fieldPattern() default "";

    String[] fields() default {};

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
