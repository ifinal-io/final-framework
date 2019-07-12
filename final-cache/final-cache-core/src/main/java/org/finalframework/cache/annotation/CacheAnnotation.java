package org.finalframework.cache.annotation;

import org.finalframework.spring.aop.annotation.CutPoint;

import java.lang.annotation.*;

/**
 * Cache 标记注释，只是用于标记 {@link Annotation} 是缓存注释。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-02 21:55:29
 * @see Cacheable
 * @see CacheLock
 * @see CacheIncrement
 * @see CachePut
 * @see CacheValue
 * @see CacheDel
 * @since 1.0
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface CacheAnnotation {
    /**
     * 仅描述该缓存 {@link Annotation} 的调用时机。
     */
    CutPoint[] value();
}
