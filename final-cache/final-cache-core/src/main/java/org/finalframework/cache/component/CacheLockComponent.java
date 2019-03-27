package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheLock;
import org.finalframework.cache.builder.CacheLockAnnotationBuilder;
import org.finalframework.cache.handler.CacheLockInvocationHandler;
import org.finalframework.cache.invocation.CacheLockInvocation;
import org.finalframework.cache.operation.CacheLockOperation;
import org.finalframework.spring.aop.interceptor.BaseOperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
public class CacheLockComponent extends BaseOperationComponent<CacheLock, CacheLockOperation,
        CacheLockAnnotationBuilder, CacheLockInvocation, CacheLockInvocationHandler> {

    public CacheLockComponent() {
        super(CacheLock.class, new CacheLockAnnotationBuilder(), new CacheLockInvocation(), new CacheLockInvocationHandler());
    }
}
