package org.finalframework.spring.aop.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.spring.aop.OperationInvocation;
import org.finalframework.spring.aop.OperationInvocationHandler;
import org.finalframework.spring.aop.OperationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:01:13
 * @since 1.0
 */
public class OperationInterceptor implements MethodInterceptor, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(OperationInterceptor.class);

    private final OperationSource source;
    private final OperationInvocationHandler handler;

    public OperationInterceptor(OperationSource source, OperationInvocationHandler handler) {
        this.source = source;
        this.handler = handler;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return new OperationInvocation(source, handler, invocation).proceed();
    }
}
