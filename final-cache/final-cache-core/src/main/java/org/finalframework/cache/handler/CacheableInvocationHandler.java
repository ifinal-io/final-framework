package org.finalframework.cache.handler;


import org.finalframework.cache.*;
import org.finalframework.cache.operation.CacheableOperation;
import org.finalframework.core.Assert;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
@SuppressWarnings("all")
public class CacheableInvocationHandler implements CacheInvocationHandler {

    @Override
    public Object handleBefore(CacheOperationContexts contexts, Object result) {
        final Collection<CacheOperationContext> cacheOperationContexts = contexts.get(CacheableOperation.class);
        if (Assert.isEmpty(cacheOperationContexts)) {
            return null;
        }
        final CacheConfiguration cacheConfiguration = contexts.configuration();
        for (CacheOperationContext context : cacheOperationContexts) {
            final Class<? extends CacheInvocation> invocation = context.operation().invocation();
            final CacheInvocation cacheInvocation = cacheConfiguration.getCacheInvocation(invocation);
            final Cache cache = CacheRegistry.getInstance().getCache(context.operation());
            final Object cacheValue = cacheInvocation.before(cache, context, result);
            if (cacheValue != null) {
                return cacheValue;
            }
        }
        return null;
    }

    @Override
    public void handleAfterReturning(CacheOperationContexts contexts, Object result) {
        final Collection<CacheOperationContext> cacheOperationContexts = contexts.get(CacheableOperation.class);
        if (Assert.isEmpty(cacheOperationContexts)) {
            return;
        }
        final CacheConfiguration cacheConfiguration = contexts.configuration();
        for (CacheOperationContext context : cacheOperationContexts) {
            final CacheOperation operation = context.operation();
            final Class<? extends CacheInvocation> invocation = operation.invocation();
            final CacheInvocation cacheInvocation = cacheConfiguration.getCacheInvocation(invocation);
            final Cache cache = CacheRegistry.getInstance().getCache(operation);
            cacheInvocation.afterReturning(cache, context, result);
        }
    }


}
