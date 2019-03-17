package org.finalframework.cache.handler;


import org.finalframework.cache.operation.CacheLockOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
public class CacheLockInvocationHandler extends VoidCacheInvocationHandler<CacheLockOperation> {

    public CacheLockInvocationHandler() {
        super(CacheLockOperation.class);
    }
}
