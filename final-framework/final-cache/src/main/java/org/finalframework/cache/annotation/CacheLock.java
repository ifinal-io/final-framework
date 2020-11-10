package org.finalframework.cache.annotation;

import org.finalframework.aop.OperationHandler;
import org.finalframework.aop.annotation.CutPoint;
import org.finalframework.cache.Cache;
import org.finalframework.cache.builder.CacheLockAnnotationBuilder;
import org.finalframework.cache.handler.CacheLockOperationHandler;
import org.finalframework.cache.operation.CacheLockOperation;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存锁，在方法执行之前尝试获取 {@link #key} 并且值为 {@link #value()} 所表示的锁，并设置锁的过期时间为 {@link #ttl()},单位为 {@link #timeunit()}。
 * 有重试机制，重试次数为 {@link #retry()}，间隔 {@link #sleep()},单位为 {@link TimeUnit#MILLISECONDS}.
 *
 * <pre>
 *     <code>
 *         public interface Service{
 *              @CacheLock(key="cache:lock:{#param.id}")
 *             void lock(Param param);
 *         }
 *     </code>
 * </pre>
 *
 * @author likly
 * @date 2019-03-06 22:12:11
 * @see Cache#lock(Object, Object, Long, TimeUnit)
 * @see Cache#unlock(Object, Object)
 * @see CacheLockOperation
 * @see CacheLockAnnotationBuilder
 * @see CacheLockOperationHandler
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER_RETURNING})
public @interface CacheLock {
    /**
     * 缓存锁 key
     */
    @NonNull
    String[] key();

    /**
     * 缓存锁 value
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
     */
    int retry() default 1;

    /**
     * 重试间隔，单位 {@link TimeUnit#MILLISECONDS}
     */
    long sleep() default 1000;

    Class<? extends OperationHandler> handler() default CacheLockOperationHandler.class;

    Class<? extends Cache> executor() default Cache.class;
}
