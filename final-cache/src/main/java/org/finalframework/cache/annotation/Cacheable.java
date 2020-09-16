package org.finalframework.cache.annotation;


import org.finalframework.cache.Cache;
import org.finalframework.cache.builder.CacheableAnnotationBuilder;
import org.finalframework.cache.handler.CacheableOperationHandler;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * 缓存，为目标方法增加缓存能力。
 * <ol>
 * <li>在执行之前，优先从 {@link #key()} 和 {@link #field()} 所描述的缓存区域里读取 {@link Cache#get(Object, Object, Type, Class)}，如果命中，则直接返回。</li>
 * <li>在执行之后，将方法的执行结果写到 {@link #key()} 和 {@link #field()} 所描述的缓存区域里，{@link Cache#set(Object, Object, Object, Long, TimeUnit, Class)}。</li>
 * </ol>
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-31 18:21
 * @see Cache#set(Object, Object, Object, Long, TimeUnit, Class)
 * @see Cache#get(Object, Object, Type, Class)
 * @see CacheableAnnotationBuilder
 * @see CacheableOperationHandler
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheAnnotation({CutPoint.BEFORE, CutPoint.AFTER_RETURNING})
public @interface Cacheable {

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

    Class<? extends OperationHandler> handler() default CacheableOperationHandler.class;

    Class<? extends Cache> executor() default Cache.class;

}
