package org.finalframework.cache.annotation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.annotation.enums.InvocationTime;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存锁，在方法执行之前尝试获取 {@link #key} 并且值为 {@link #value()} 所表示的锁，并设置锁的过期时间为 {@link #ttl()},单位为 {@link #timeunit()}。
 * 有重试机制，重试次数为 {@link #retry()}，间隔 {@link #sleep()},单位为 {@link TimeUnit#MILLISECONDS}.
 *
 * @author likly
 * @date 2019-03-06 22:12:11
 * @see Cache#lock(Object, Object, Long, TimeUnit)
 * @see Cache#unlock(Object, Object)
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheAnnotation({InvocationTime.BEFORE, InvocationTime.AFTER_RETURNING})
public @interface CacheLock {
    String[] key();

    String value() default "";

    String delimiter() default ":";

    String condition() default "";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 重试次数
     */
    int retry() default 1;

    /**
     * 重试间隔，单位 {@link TimeUnit#MILLISECONDS}
     */
    long sleep() default 1000;

    Class<? extends CacheInvocation> invocation() default CacheInvocation.class;
}
