package org.finalframework.cache.handler;


import org.finalframework.cache.*;
import org.finalframework.cache.annotation.enums.CacheInvocationTime;
import org.finalframework.core.Assert;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
public class VoidCacheOperationContextsHandler<T extends CacheOperation> implements CacheOperationContextsHandler<Void> {

    private final Class<T> type;

    public VoidCacheOperationContextsHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Void handle(CacheOperationContexts contexts, Object result, CacheInvocationTime invocationTime) {
        final Collection<CacheOperationContext> cacheOperationContexts = contexts.get(type);
        if (Assert.isEmpty(cacheOperationContexts)) {
            return null;
        }

        final CacheConfiguration cacheConfiguration = contexts.configuration();

        for (CacheOperationContext context : cacheOperationContexts) {
            final Class<? extends CacheInvocation> invocation = context.operation().invocation();
            final CacheInvocation cacheInvocation = cacheConfiguration.getCacheInvocation(invocation);
            final Cache cache = CacheRegistry.getInstance().getCache(context.operation());
            if (cacheInvocation.supports(context, invocationTime)) {
                cacheInvocation.invocation(cache, context, result, invocationTime);
            }

        }

        return null;
    }
}
