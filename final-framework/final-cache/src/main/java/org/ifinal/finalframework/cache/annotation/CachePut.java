package org.ifinal.finalframework.cache.annotation;


import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.handler.CachePutOperationHandler;

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
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER, CutPoint.AFTER_RETURNING, CutPoint.AFTER_THROWING})
public @interface CachePut {

    String[] key();

    String[] field() default {};

    String value() default "";

    String delimiter() default ":";

    CutPoint point() default CutPoint.AFTER_RETURNING;

    String condition() default "";

    String expire() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    Class<? extends OperationHandler> handler() default CachePutOperationHandler.class;

    Class<? extends Cache> executor() default Cache.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CachePut[] value();
    }
}
