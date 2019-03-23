package org.finalframework.cache.annotation;

import org.finalframework.cache.CacheInvocation;
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
@CacheAnnotation({Order.BEFORE, Order.AFTER, Order.AFTER_RETURNING, Order.AFTER_THROWING})
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

    Order order() default Order.AFTER_RETURNING;

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

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CacheIncrement[] value();
    }

}
