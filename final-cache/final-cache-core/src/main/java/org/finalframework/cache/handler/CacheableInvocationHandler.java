package org.finalframework.cache.handler;


import org.finalframework.cache.operation.CacheableOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocationHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
@SuppressWarnings("all")
public final class CacheableInvocationHandler extends BaseInvocationHandler<CacheableOperation> {
    public CacheableInvocationHandler() {
        super(CacheableOperation.class);
    }
}
