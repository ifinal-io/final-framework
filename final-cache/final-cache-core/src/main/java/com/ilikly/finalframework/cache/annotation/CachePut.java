package com.ilikly.finalframework.cache.annotation;


import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheInvocation;
import com.ilikly.finalframework.cache.annotation.enums.CacheInvocationTime;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @see Cache#set(Object, Object, Object, Long, TimeUnit, Class)
 * @since 1.0
 */
@Documented
@CacheAnnotation(CacheInvocationTime.AFTER)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CachePut {

    String[] key();

    String[] field() default {};

    String value() default "";

    String delimiter() default ":";

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    Class<? extends CacheInvocation> invocation() default CacheInvocation.class;
}
