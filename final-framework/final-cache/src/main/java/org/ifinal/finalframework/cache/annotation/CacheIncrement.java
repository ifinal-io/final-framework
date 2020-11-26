package org.ifinal.finalframework.cache.annotation;

import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.builder.CacheIncrementAnnotationBuilder;
import org.ifinal.finalframework.cache.handler.CacheIncrementOperationHandler;
import org.ifinal.finalframework.cache.operation.CacheIncrementOperation;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0.0
 * @see Cache#increment(Object, Object, Long)
 * @see Cache#increment(Object, Object, Double)
 * @see CacheIncrementOperation
 * @see CacheIncrementAnnotationBuilder
 * @see CacheIncrementOperationHandler
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CacheIncrement.List.class)
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER, CutPoint.AFTER_RETURNING, CutPoint.AFTER_THROWING})
public @interface CacheIncrement {

    /**
     * 缓存区
     *
     * @return key
     */
    String[] key();

    /**
     * 缓存域
     *
     * @return field
     */
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
    @AliasFor("when")
    String condition() default "";

    /**
     * 缓存条件
     *
     * @return condition
     * @see #condition()
     */
    @AliasFor("condition")
    String when() default "";

    CutPoint point() default CutPoint.AFTER_RETURNING;

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

    Class<? extends OperationHandler> handler() default CacheIncrementOperationHandler.class;


    Class<? extends Cache> executor() default Cache.class;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CacheIncrement[] value();
    }

}
