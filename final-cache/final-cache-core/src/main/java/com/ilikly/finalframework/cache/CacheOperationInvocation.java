package com.ilikly.finalframework.cache;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 21:53:13
 * @since 1.0
 * @see com.ilikly.finalframework.cache.interceptor.CacheLockOperationInvocation
 * @see com.ilikly.finalframework.cache.interceptor.CacheableOperationInvocation
 * @see com.ilikly.finalframework.cache.interceptor.CacheGetOperationInvocation
 * @see com.ilikly.finalframework.cache.interceptor.CacheDelOperationInvocation
 */
public interface CacheOperationInvocation<T> {
    T invoke(Cache cache, CacheOperationInvocationContext context, Object result);
}
