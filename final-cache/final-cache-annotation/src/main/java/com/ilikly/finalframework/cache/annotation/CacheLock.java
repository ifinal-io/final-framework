package com.ilikly.finalframework.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存锁，在方法执行之前尝试获取 {@link #key} 并且值为 {@link #value()} 所表示的锁，并设置锁的过期时间为 {@link #ttl()},单位为 {@link #timeunit()}。
 * 有重试机制，重试次数为 {@link #retry()}，间隔 {@link #sleep()},单位为 {@link TimeUnit#MILLISECONDS}.
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-06 22:12:11
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheAnnotation
public @interface CacheLock {
    String[] key();

    String value() default "";

    String delimiter() default ":";

    long ttl() default -1L;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    int retry() default 1;

    long sleep() default 1000;
}
