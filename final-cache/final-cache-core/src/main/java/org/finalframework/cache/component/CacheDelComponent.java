package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheDel;
import org.finalframework.cache.builder.CacheDelAnnotationBuilder;
import org.finalframework.cache.handler.CacheDelOperationHandler;
import org.finalframework.cache.invocation.CacheDelInvocation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationComponent;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
@Order(CacheOrder.CACHE_DEL)
@SpringComponent
public final class CacheDelComponent extends OperationComponent {
    protected CacheDelComponent() {
        super(OperationComponent.builder()
                .annotation(CacheDel.class)
                .builder(CacheDelAnnotationBuilder.class)
                .handler(CacheDelOperationHandler.class)
                .invocation(CacheDelInvocation.class));
    }
}