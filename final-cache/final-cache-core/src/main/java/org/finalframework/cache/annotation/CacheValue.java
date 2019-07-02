package org.finalframework.cache.annotation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.handler.CacheValueOperationHandler;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

/**
 * 作用在方法参数 {@link java.lang.reflect.Parameter} 上，实现在方法执行之前，从缓存中获取值，并将该值赋于该参数。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-22 14:34:17
 * @since 1.0
 * @see org.finalframework.cache.Cache#get(Object, Object, Type, Class)
 */
@CacheAnnotation(CutPoint.BEFORE)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheValue {

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

    Class<? extends OperationHandler> handler() default CacheValueOperationHandler.class;

    Class<? extends Cache> executor() default Cache.class;
}
