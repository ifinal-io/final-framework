package com.ilikly.finalframework.cache.handler;


import com.ilikly.finalframework.cache.operation.CachePutOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:25:28
 * @since 1.0
 */
public class CachePutOperationContextsHandler extends VoidCacheOperationContextsHandler<CachePutOperation> {

    public CachePutOperationContextsHandler() {
        super(CachePutOperation.class);
    }
}
