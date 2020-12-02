package org.ifinal.finalframework.cache.annotation;

import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.handler.CacheLockInterceptorHandler;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存锁，在方法执行之前尝试获取 {@link #key} 并且值为 {@link #value()} 所表示的锁，并设置锁的过期时间为 {@link #ttl()},单位为 {@link #timeunit()}。
 * 有重试机制，重试次数为 {@link #retry()}，间隔 {@link #sleep()},单位为 {@link TimeUnit#MILLISECONDS}.
 *
 * @author likly
 * @see Cache#lock(Object, Object, Long, TimeUnit)
 * @see Cache#unlock(Object, Object)
 * @see CacheLockInterceptorHandler
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheAnnotation({JoinPoint.BEFORE, JoinPoint.AFTER_RETURNING})
public @interface CacheLock {
    /**
     * 缓存锁 key
     *
     * @return key
     */
    @NonNull
    String[] key();

    /**
     * 缓存锁 value
     *
     * @return value
     */
    @Nullable
    String value() default "";


    @NonNull
    String delimiter() default ":";

    @Nullable
    String condition() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    int order() default Ordered.HIGHEST_PRECEDENCE;

    /**
     * 重试次数
     *
     * @return retry
     */
    int retry() default 1;

    /**
     * 重试间隔，单位 {@link TimeUnit#MILLISECONDS}
     *
     * @return sleep
     */
    long sleep() default 1000;

    Class<? extends InterceptorHandler> handler() default CacheLockInterceptorHandler.class;

    Class<? extends Cache> executor() default Cache.class;
}
