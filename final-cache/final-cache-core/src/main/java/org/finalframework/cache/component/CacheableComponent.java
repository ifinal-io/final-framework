package org.finalframework.cache.component;


import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.builder.CacheableAnnotationBuilder;
import org.finalframework.cache.handler.CacheableOperationHandler;
import org.finalframework.cache.invocation.CacheableInvocation;
import org.finalframework.cache.operation.CacheableOperation;
import org.finalframework.spring.aop.interceptor.BaseOperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
public class CacheableComponent extends BaseOperationComponent<Cacheable, CacheableOperation,
        CacheableAnnotationBuilder, CacheableInvocation, CacheableOperationHandler> {

    public CacheableComponent() {
        super(Cacheable.class, new CacheableAnnotationBuilder(), new CacheableInvocation(), new CacheableOperationHandler());
    }
}
