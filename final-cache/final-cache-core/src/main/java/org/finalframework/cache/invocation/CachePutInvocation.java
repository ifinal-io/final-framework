package org.finalframework.cache.invocation;


import org.finalframework.cache.operation.CachePutOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
public class CachePutInvocation extends BaseInvocation<CachePutOperation> {

    public CachePutInvocation() {
        super(CachePutOperation.class);
    }
}
