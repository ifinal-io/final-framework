package com.ilikly.finalframework.cache;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 21:53:13
 * @since 1.0
 */
public interface CacheOperationInvocation<T> {
    T invoke(Cache cache, CacheOperationInvocationContext context, Object result);
}
