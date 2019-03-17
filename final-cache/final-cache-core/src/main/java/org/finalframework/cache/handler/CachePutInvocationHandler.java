package org.finalframework.cache.handler;


import org.finalframework.cache.operation.CachePutOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
public class CachePutInvocationHandler extends VoidCacheInvocationHandler<CachePutOperation> {

    public CachePutInvocationHandler() {
        super(CachePutOperation.class);
    }
}
