package org.ifinal.finalframework.cache.annotation;

import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.cache.Cache;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0.0
 * @see Cache#increment(Object, Object, Long)
 * @see Cache#increment(Object, Object, Double)
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CacheIncrement.List.class)
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

    JoinPoint point() default JoinPoint.AFTER_RETURNING;

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


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CacheIncrement[] value();
    }

}
