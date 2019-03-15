package com.ilikly.finalframework.cache;

import com.ilikly.finalframework.cache.invocation.CacheDelInvocation;
import com.ilikly.finalframework.cache.invocation.CacheLockInvocation;
import com.ilikly.finalframework.cache.invocation.CachePutInvocation;
import com.ilikly.finalframework.cache.invocation.CacheableInvocation;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:35:22
 * @since 1.0
 */
public class CacheConfiguration {

    private final Map<Class<? extends CacheInvocation>, CacheInvocation> invocationMap = new ConcurrentHashMap<>(8);

    {
        registerCacheInvocation(new CacheLockInvocation());
        registerCacheInvocation(new CacheableInvocation());
        registerCacheInvocation(new CacheDelInvocation());
        registerCacheInvocation(new CachePutInvocation());

    }

    public void registerCacheInvocation(CacheInvocation invocation) {
        invocationMap.put(invocation.getClass(), invocation);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <T extends CacheInvocation> T getCacheInvocation(@NonNull Class<T> invocation) {
        return (T) invocationMap.get(invocation);
    }

}
