package org.finalframework.cache.component;


import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.builder.CacheableAnnotationBuilder;
import org.finalframework.cache.handler.CacheableOperationHandler;
import org.finalframework.cache.invocation.CacheableInvocation;
import org.finalframework.spring.aop.annotation.OperationComponent;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
@Order(CacheOrder.CACHE_ABLE)
@OperationComponent(
        annotation = Cacheable.class,
        builder = CacheableAnnotationBuilder.class,
        handler = CacheableOperationHandler.class,
        invocation = CacheableInvocation.class
)
public final class CacheableComponent {
}
