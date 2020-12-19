package org.ifinal.finalframework.annotation.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.annotation.cache.CacheIncrement.CacheIncrements;
import org.springframework.core.annotation.AliasFor;

/**
 * @author likly
 * @version 1.0.0
 * @see Cache#increment(Object, Object, Long)
 * @see Cache#increment(Object, Object, Double)
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CacheIncrements.class)
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

    /**
     * CacheIncrements.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface CacheIncrements {

        CacheIncrement[] value();

    }

}
