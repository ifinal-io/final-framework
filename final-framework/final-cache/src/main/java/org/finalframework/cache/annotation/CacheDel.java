package org.finalframework.cache.annotation;

import org.finalframework.aop.OperationHandler;
import org.finalframework.aop.annotation.CutPoint;
import org.finalframework.cache.Cache;
import org.finalframework.cache.annotation.CacheDel.List;
import org.finalframework.cache.builder.CacheDelAnnotationBuilder;
import org.finalframework.cache.handler.CacheDelOperationHandler;
import org.finalframework.cache.operation.CacheDelOperation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 在方法 {@link java.lang.reflect.Method} 执行的生命周期 {@link CutPoint}中删除命中的缓存 {@link Cache#del(Object, Object)}。
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @see Cache#del(Object, Object)
 * @see CacheDelOperation
 * @see CacheDelAnnotationBuilder
 * @see CacheDelOperationHandler
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER, CutPoint.AFTER_RETURNING, CutPoint.AFTER_THROWING})
public @interface CacheDel {

    /**
     * 缓存区表达式
     */
    String[] key();

    /**
     * 缓存域表达式
     */
    String[] field() default {};

    /**
     * 缓存区域表达式分隔符
     */
    String delimiter() default ":";

    /**
     * 执行条件
     */
    String condition() default "";

    /**
     * 重试次数
     */
    int retry() default 0;

    /**
     * 重试间隔
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
