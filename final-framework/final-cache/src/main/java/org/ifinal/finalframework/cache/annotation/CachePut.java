package org.ifinal.finalframework.cache.annotation;


import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.handler.CachePutInterceptorHandler;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * @author likly
 * @version 1.0.0
 * @see Cache#set(Object, Object, Object, Long, TimeUnit, Class)
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CachePut.List.class)
@CacheAnnotation({JoinPoint.BEFORE, JoinPoint.AFTER, JoinPoint.AFTER_RETURNING, JoinPoint.AFTER_THROWING})
public @interface CachePut {

    String[] key();

    String[] field() default {};

    String value() default "";

    String delimiter() default ":";

    JoinPoint point() default JoinPoint.AFTER_RETURNING;

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    Class<? extends InterceptorHandler> handler() default CachePutInterceptorHandler.class;

    Class<? extends Cache> executor() default Cache.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CachePut[] value();
    }
}
