package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheLock;
import org.finalframework.cache.builder.CacheLockAnnotationBuilder;
import org.finalframework.cache.handler.CacheLockOperationHandler;
import org.finalframework.cache.invocation.CacheLockInvocation;
import org.finalframework.spring.aop.annotation.OperationComponent;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
@Order(CacheOrder.CACHE_LOCK)
@OperationComponent(
        annotation = CacheLock.class,
        builder = CacheLockAnnotationBuilder.class,
        handler = CacheLockOperationHandler.class,
        invocation = CacheLockInvocation.class
)
public final class CacheLockComponent {
}