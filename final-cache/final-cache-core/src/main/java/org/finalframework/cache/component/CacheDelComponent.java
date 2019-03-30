package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheDel;
import org.finalframework.cache.builder.CacheDelAnnotationBuilder;
import org.finalframework.cache.handler.CacheDelInvocationHandler;
import org.finalframework.cache.invocation.CacheDelInvocation;
import org.finalframework.cache.operation.CacheDelOperation;
import org.finalframework.spring.aop.interceptor.BaseOperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
public class CacheDelComponent extends BaseOperationComponent<CacheDel, CacheDelOperation,
        CacheDelAnnotationBuilder, CacheDelInvocationHandler, CacheDelInvocation> {

    public CacheDelComponent() {
        super(CacheDel.class, new CacheDelAnnotationBuilder(), new CacheDelInvocationHandler(), new CacheDelInvocation());
    }
}