package org.finalframework.cache.interceptor;

import org.finalframework.cache.CacheOperation;
import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 16:01:12
 * @since 1.0
 */
public class CacheOperationCacheKey implements Comparable<CacheOperationCacheKey> {

    private final CacheOperation cacheOperation;

    private final AnnotatedElementKey methodCacheKey;

    CacheOperationCacheKey(CacheOperation cacheOperation, Method method, Class<?> targetClass) {
        this.cacheOperation = cacheOperation;
        this.methodCacheKey = new AnnotatedElementKey(method, targetClass);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CacheOperationCacheKey)) {
            return false;
        }
        CacheOperationCacheKey otherKey = (CacheOperationCacheKey) other;
        return (this.cacheOperation.equals(otherKey.cacheOperation) &&
                this.methodCacheKey.equals(otherKey.methodCacheKey));
    }

    @Override
    public int hashCode() {
        return (this.cacheOperation.hashCode() * 31 + this.methodCacheKey.hashCode());
    }

    @Override
    public String toString() {
        return this.cacheOperation + " on " + this.methodCacheKey;
    }

    @Override
    public int compareTo(CacheOperationCacheKey other) {
        int result = this.cacheOperation.getClass().getSimpleName().compareTo(other.cacheOperation.getClass().getSimpleName());
        if (result == 0) {
            result = this.methodCacheKey.compareTo(other.methodCacheKey);
        }
        return result;
    }
}
