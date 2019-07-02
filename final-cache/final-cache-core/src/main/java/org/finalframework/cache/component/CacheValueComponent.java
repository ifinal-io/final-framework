package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheValue;
import org.finalframework.cache.builder.CacheValueAnnotationBuilder;
import org.finalframework.cache.handler.CacheValueOperationHandler;
import org.finalframework.cache.invocation.CacheValueInvocation;
import org.finalframework.cache.operation.CacheValueOperation;
import org.finalframework.spring.aop.interceptor.BaseOperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
public class CacheValueComponent extends BaseOperationComponent<CacheValue, CacheValueOperation,
        CacheValueAnnotationBuilder, CacheValueInvocation, CacheValueOperationHandler> {

    public CacheValueComponent() {
        super(CacheValue.class, new CacheValueAnnotationBuilder(), new CacheValueInvocation(), new CacheValueOperationHandler());
    }
}
