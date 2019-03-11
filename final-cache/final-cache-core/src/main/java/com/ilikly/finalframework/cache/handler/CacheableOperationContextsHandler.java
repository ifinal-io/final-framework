package com.ilikly.finalframework.cache.handler;


import com.ilikly.finalframework.cache.*;
import com.ilikly.finalframework.cache.annotation.enums.CacheInvocationTime;
import com.ilikly.finalframework.cache.operation.CacheableOperation;
import com.ilikly.finalframework.core.Assert;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
public class CacheableOperationContextsHandler implements CacheOperationContextsHandler<Object> {

    @Override
    @SuppressWarnings("unchecked")
    public Object handle(CacheOperationContexts contexts, Object result, CacheInvocationTime invocationTime) {
        final Collection<CacheOperationContext> cacheOperationContexts = contexts.get(CacheableOperation.class);
        if (Assert.isEmpty(cacheOperationContexts)) {
            return null;
        }

        final CacheConfiguration cacheConfiguration = contexts.configuration();

        for (CacheOperationContext context : cacheOperationContexts) {
            final Class<? extends CacheInvocation> invocation = context.operation().invocation();
            final CacheInvocation cacheInvocation = cacheConfiguration.getCacheInvocation(invocation);
            final Cache cache = CacheRegistry.getInstance().getCache(context.operation());
            if (cacheInvocation.supports(context, invocationTime)) {
                final Object cacheValue = cacheInvocation.invocation(cache, context, result, invocationTime);
                if (CacheInvocationTime.BEFORE == invocationTime && cacheValue != null) {
                    return cacheValue;
                }
            }
        }
        return null;
    }
}
