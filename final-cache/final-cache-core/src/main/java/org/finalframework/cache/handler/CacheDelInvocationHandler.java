package org.finalframework.cache.handler;


import org.finalframework.cache.operation.CacheDelOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocationHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
public class CacheDelInvocationHandler extends BaseInvocationHandler<CacheDelOperation> {

    public CacheDelInvocationHandler() {
        super(CacheDelOperation.class);
    }

}
