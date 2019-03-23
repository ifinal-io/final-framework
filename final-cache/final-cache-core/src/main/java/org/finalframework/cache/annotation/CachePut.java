package org.finalframework.cache.annotation;


import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;

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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CachePut.List.class)
@CacheAnnotation({Order.BEFORE, Order.AFTER, Order.AFTER_RETURNING, Order.AFTER_THROWING})
public @interface CachePut {

    String[] key();

    String[] field() default {};

    String value() default "";

    String delimiter() default ":";

    Order order() default Order.AFTER_RETURNING;

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    Class<? extends CacheInvocation> invocation() default CacheInvocation.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CachePut[] value();
    }
}
