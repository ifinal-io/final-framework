package org.finalframework.cache.invocation;


import org.finalframework.cache.operation.CacheIncrementOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 23:47:49
 * @since 1.0
 */
public class CacheIncrementInvocation extends BaseInvocation<CacheIncrementOperation> {

    public CacheIncrementInvocation() {
        super(CacheIncrementOperation.class);
    }
}
