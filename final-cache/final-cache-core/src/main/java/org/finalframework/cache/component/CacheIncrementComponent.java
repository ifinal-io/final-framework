package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheIncrement;
import org.finalframework.cache.builder.CacheIncrementAnnotationBuilder;
import org.finalframework.cache.handler.CacheIncrementOperationHandler;
import org.finalframework.cache.invocation.CacheIncrementInvocation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationComponent;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
@Order(CacheOrder.CACHE_INCREMENT)
@SpringComponent
public final class CacheIncrementComponent extends OperationComponent {
    protected CacheIncrementComponent() {
        super(OperationComponent.builder()
                .annotation(CacheIncrement.class)
                .builder(CacheIncrementAnnotationBuilder.class)
                .handler(CacheIncrementOperationHandler.class)
                .invocation(CacheIncrementInvocation.class));
    }
}