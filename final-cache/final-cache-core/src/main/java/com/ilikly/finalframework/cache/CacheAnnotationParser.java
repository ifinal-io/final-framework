package com.ilikly.finalframework.cache;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 缓存注释解析器，将声明在{@link Class} 和 {@link Method} 元素上的 {@link java.lang.annotation.Annotation}解析成其所对应的 {@link CacheOperation}。
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:14:24
 * @since 1.0
 */
public interface CacheAnnotationParser {

    /**
     * 找出标记于 {@link Class} 上的缓存 {@link java.lang.annotation.Annotation}，前将其解析成其所对应的 {@link CacheOperation}。
     *
     * @param type 被 {@link java.lang.annotation.Annotation} 标记的 {@link Class} 元素。
     * @return 标记于类元素 {@link Class} 上的 {@link java.lang.annotation.Annotation} 所对应的 {@link CacheOperation} 集合。
     */
    @Nullable
    Collection<CacheOperation> parseCacheAnnotation(Class<?> type);

    /**
     * 找出标记于 {@link Method} 上的缓存 {@link java.lang.annotation.Annotation}，前将其解析成其所对应的 {@link CacheOperation}。
     *
     * @param method 被 {@link java.lang.annotation.Annotation} 标记的 {@link Method} 元素。
     * @return 标记于方法元素 {@link Method} 上的 {@link java.lang.annotation.Annotation} 所对应的 {@link CacheOperation} 集合。
     */
    @Nullable
    Collection<CacheOperation> parseCacheAnnotation(Method method);

}
