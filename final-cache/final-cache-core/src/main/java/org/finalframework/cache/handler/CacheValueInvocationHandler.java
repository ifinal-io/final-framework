package org.finalframework.cache.handler;


import org.finalframework.cache.*;
import org.finalframework.cache.operation.CacheValueOperation;
import org.finalframework.core.Assert;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
@SuppressWarnings("all")
public class CacheValueInvocationHandler implements CacheInvocationHandler {

    @Override
    public Void handleBefore(CacheOperationContexts contexts, Object result) {
        final Collection<CacheOperationContext> cacheOperationContexts = contexts.get(CacheValueOperation.class);
        if (Assert.isEmpty(cacheOperationContexts)) {
            return null;
        }
        final CacheConfiguration cacheConfiguration = contexts.configuration();
        for (CacheOperationContext context : cacheOperationContexts) {
            final Class<? extends CacheInvocation> invocation = context.operation().invocation();
            final CacheInvocation cacheInvocation = cacheConfiguration.getCacheInvocation(invocation);
            final Cache cache = CacheRegistry.getInstance().getCache(context.operation());
            cacheInvocation.before(cache, context, result);
        }
        return null;
    }


}
