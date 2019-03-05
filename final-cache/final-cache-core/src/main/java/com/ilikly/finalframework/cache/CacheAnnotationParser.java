package com.ilikly.finalframework.cache;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:14:24
 * @since 1.0
 */
public interface CacheAnnotationParser {

    @Nullable
    Collection<CacheOperation> parseCacheAnnotation(Class<?> type);

    @Nullable
    Collection<CacheOperation> parseCacheAnnotation(Method method);

}
