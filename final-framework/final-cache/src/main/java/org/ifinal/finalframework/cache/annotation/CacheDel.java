package org.ifinal.finalframework.cache.annotation;

import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.CacheDel.List;
import org.ifinal.finalframework.cache.handler.CacheDelOperationHandler;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 在方法 {@link java.lang.reflect.Method} 执行的生命周期 {@link CutPoint}中删除命中的缓存 {@link Cache#del(Object, Object)}。
 *
 * @author likly
 * @version 1.0.0
 * @see Cache#del(Object, Object)
 * @see CacheDelOperationHandler
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER, CutPoint.AFTER_RETURNING, CutPoint.AFTER_THROWING})
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

    CutPoint point() default CutPoint.AFTER_RETURNING;

    Class<? extends OperationHandler> handler() default CacheDelOperationHandler.class;


    Class<? extends Cache> executor() default Cache.class;

    /**
     * Defines several {@link CacheDel } annotations on the same element.
     *
     * @see CacheDel
     */
    @Target({METHOD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CacheDel[] value();
    }
}
