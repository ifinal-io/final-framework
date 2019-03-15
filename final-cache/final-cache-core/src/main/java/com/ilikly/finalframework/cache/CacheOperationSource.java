package com.ilikly.finalframework.cache;

import org.springframework.core.MethodClassKey;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 10:01:33
 * @since 1.0
 */
public interface CacheOperationSource {

    @Nullable
    Collection<CacheOperation> getCacheOperations(Method method, @Nullable Class<?> targetClass);

    @NonNull
    default Object getCacheKey(Method method, @Nullable Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }

}
