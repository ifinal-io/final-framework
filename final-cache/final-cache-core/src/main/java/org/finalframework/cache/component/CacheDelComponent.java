package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheDel;
import org.finalframework.cache.builder.CacheDelAnnotationBuilder;
import org.finalframework.cache.handler.CacheDelOperationHandler;
import org.finalframework.cache.invocation.CacheDelInvocation;
import org.finalframework.spring.aop.annotation.OperationComponent;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
@Order(CacheOrder.CACHE_DEL)
@OperationComponent(
        annotation = CacheDel.class,
        builder = CacheDelAnnotationBuilder.class,
        handler = CacheDelOperationHandler.class,
        invocation = CacheDelInvocation.class
)
public final class CacheDelComponent {
}
