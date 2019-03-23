package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CachePut;
import org.finalframework.cache.builder.CachePutAnnotationBuilder;
import org.finalframework.cache.handler.CachePutInvocationHandler;
import org.finalframework.cache.invocation.CachePutInvocation;
import org.finalframework.cache.operation.CachePutOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
public class CachePutComponent extends AbsCacheComponent<CachePut, CachePutOperation,
        CachePutAnnotationBuilder, CachePutInvocation, CachePutInvocationHandler> {

    public CachePutComponent() {
        super(CachePut.class, new CachePutAnnotationBuilder(), new CachePutInvocation(), new CachePutInvocationHandler());
    }
}
