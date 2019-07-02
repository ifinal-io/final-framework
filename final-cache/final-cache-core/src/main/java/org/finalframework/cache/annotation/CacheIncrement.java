package org.finalframework.cache.annotation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.handler.CacheIncrementOperationHandler;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-21 22:19:50
 * @see org.finalframework.cache.Cache#increment(Object, Object, Long)
 * @see org.finalframework.cache.Cache#increment(Object, Object, Double)
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CacheIncrement.List.class)
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER, CutPoint.AFTER_RETURNING, CutPoint.AFTER_THROWING})
public @interface CacheIncrement {

    /**
     * 缓存区
     */
    String[] key();

    /**
     * 缓存域
     */
    String[] field() default {};

    /**
     * 缓存区域的分隔符
     */
    String delimiter() default ":";

    String value() default "{1}";

    Class<? extends Number> type() default int.class;

    /**
     * 缓存条件
     *
     * @see #when()
     */
    @AliasFor("when")
    String condition() default "";

    /**
     * 缓存条件
     *
     * @see #condition()
     */
    @AliasFor("condition")
    String when() default "";

    CutPoint point() default CutPoint.AFTER_RETURNING;

    /**
     * 过期时间
     */
    String expire() default "";

    /**
     * 有效时间
     */
    long ttl() default -1L;

    /**
     * 有效时间单位
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
