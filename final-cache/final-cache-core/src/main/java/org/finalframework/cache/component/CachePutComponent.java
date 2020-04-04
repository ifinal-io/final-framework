package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CachePut;
import org.finalframework.cache.builder.CachePutAnnotationBuilder;
import org.finalframework.cache.handler.CachePutOperationHandler;
import org.finalframework.cache.invocation.CachePutInvocation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationComponent;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
@Order(CacheOrder.CACHE_PUT)
@SpringComponent
public final class CachePutComponent extends OperationComponent {
    protected CachePutComponent() {
        super(OperationComponent.builder()
                .annotation(CachePut.class)
                .builder(CachePutAnnotationBuilder.class)
                .handler(CachePutOperationHandler.class)
                .invocation(CachePutInvocation.class));
    }
}