package org.finalframework.cache.annotation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.annotation.enums.InvocationTime;

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
@CacheAnnotation({InvocationTime.BEFORE, InvocationTime.AFTER_RETURNING})
public @interface CacheDel {

    String[] key();

    String[] field() default {};

    String delimiter() default ":";

    String condition() default "";

    int retry() default 0;

    long sleep() default 1000;

    InvocationTime invocationTime() default InvocationTime.AFTER_RETURNING;

    Class<? extends CacheInvocation> invocation() default CacheInvocation.class;

}
