package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheValue;
import org.finalframework.cache.builder.CacheValueAnnotationBuilder;
import org.finalframework.cache.handler.CacheValueOperationHandler;
import org.finalframework.cache.invocation.CacheValueInvocation;
import org.finalframework.spring.aop.annotation.OperationComponent;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
@Order(CacheOrder.CACHE_VALUE)
@OperationComponent(
        annotation = CacheValue.class,
        builder = CacheValueAnnotationBuilder.class,
        handler = CacheValueOperationHandler.class,
        invocation = CacheValueInvocation.class
)
public final class CacheValueComponent {
}
