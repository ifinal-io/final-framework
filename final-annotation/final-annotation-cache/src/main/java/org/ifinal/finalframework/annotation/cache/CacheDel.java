package org.ifinal.finalframework.annotation.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.annotation.cache.CacheDel.CacheDels;

/**
 * 在方法 {@link java.lang.reflect.Method} 执行的生命周期 {@link JoinPoint}中删除命中的缓存 {@link Cache#del(Object, Object)}。
 *
 * @author likly
 * @version 1.0.0
 * @see Cache#del(Object, Object)
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CacheDels.class)
public @interface CacheDel {

    /**
     * 缓存区表达式
     *
     * @return key
     */
    String[] key();

    /**
     * 缓存域表达式
     *
     * @return field
     */
    String[] field() default {};

    /**
     * 缓存区域表达式分隔符
     *
     * @return delimiter
     */
    String delimiter() default ":";

    /**
     * 执行条件
     *
     * @return condition
     */
    String condition() default "";

    /**
     * 重试次数
     *
     * @return retry
     */
    int retry() default 0;

    /**
     * 重试间隔
     *
     * @return sleep
     */
    long sleep() default 1000;

    JoinPoint point() default JoinPoint.AFTER_RETURNING;

    /**
     * Defines several {@link CacheDel } annotations on the same element.
     *
     * @see CacheDel
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface CacheDels {

        CacheDel[] value();

    }

}
