package com.ilikly.finalframework.cache.annotation;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheInvocation;
import com.ilikly.finalframework.cache.annotation.enums.CacheInvocationTime;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @see Cache#del(Object, Object)
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheAnnotation({CacheInvocationTime.BEFORE, CacheInvocationTime.AFTER})
public @interface CacheDel {

    String[] key();

    String[] field() default {};

    String delimiter() default ":";

    String condition() default "";

    int retry() default 0;

    long sleep() default 1000;

    Class<? extends CacheInvocation> invocation() default CacheInvocation.class;

}
