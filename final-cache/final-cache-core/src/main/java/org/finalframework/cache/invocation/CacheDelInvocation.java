package org.finalframework.cache.invocation;


import org.finalframework.cache.handler.CacheDelOperationHandler;
import org.finalframework.cache.operation.CacheDelOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @see org.finalframework.cache.annotation.CacheDel
 * @see CacheDelOperationHandler
 * @since 1.0
 */
public class CacheDelInvocation extends BaseInvocation<CacheDelOperation> {

    public CacheDelInvocation() {
        super(CacheDelOperation.class);
    }

}
