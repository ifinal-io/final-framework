package org.finalframework.cache.annotation;

import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.annotation.enums.InvocationTime;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-21 22:19:50
 * @since 1.0
 */
@CacheAnnotation({InvocationTime.BEFORE, InvocationTime.AFTER, InvocationTime.AFTER_RETURNING, InvocationTime.AFTER_THROWING})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
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

    InvocationTime invocationTime() default InvocationTime.AFTER_RETURNING;

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


    Class<? extends CacheInvocation> invocation() default CacheInvocation.class;

}
