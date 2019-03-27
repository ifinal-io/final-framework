package org.finalframework.cache.handler;


import org.finalframework.cache.operation.CacheIncrementOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocationHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 23:47:49
 * @since 1.0
 */
public class CacheIncrementInvocationHandler extends BaseInvocationHandler<CacheIncrementOperation> {

    public CacheIncrementInvocationHandler() {
        super(CacheIncrementOperation.class);
    }
}
