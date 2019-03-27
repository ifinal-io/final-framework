package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheIncrement;
import org.finalframework.cache.builder.CacheIncrementAnnotationBuilder;
import org.finalframework.cache.handler.CacheIncrementInvocationHandler;
import org.finalframework.cache.invocation.CacheIncrementInvocation;
import org.finalframework.cache.operation.CacheIncrementOperation;
import org.finalframework.spring.aop.interceptor.BaseOperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
public class CacheIncrementComponent extends BaseOperationComponent<CacheIncrement, CacheIncrementOperation,
        CacheIncrementAnnotationBuilder, CacheIncrementInvocation, CacheIncrementInvocationHandler> {

    public CacheIncrementComponent() {
        super(CacheIncrement.class, new CacheIncrementAnnotationBuilder(), new CacheIncrementInvocation(), new CacheIncrementInvocationHandler());
    }
}
