package org.finalframework.spring.aop;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.core.Assert;
import org.finalframework.spring.aop.interceptor.BaseOperationInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:01:13
 * @since 1.0
 */
public class OperationInterceptor implements MethodInterceptor, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(OperationInterceptor.class);

    private final OperationConfiguration configuration;

    public OperationInterceptor(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final Object invoke(MethodInvocation invocation) throws Throwable {
        return invoke(new BaseOperationInvocation(configuration, invocation));
    }

    public final Object invoke(OperationInvocation invocation) throws Throwable {
        final Collection<OperationContext<Operation>> contexts = invocation.getOperationContexts();

        if (Assert.isEmpty(contexts)) {
            return invocation.proceed();
        }
        final OperationInvocationHandler handler = configuration.getInvocationHandler();
        Object operationValue = handler.handleBefore(contexts);
        if (operationValue != null) {
            return operationValue;
        }

        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = invocation.proceed();
        } catch (Throwable e) {
            throwable = e;
        }

        if (throwable == null) {
            handler.handleAfterReturning(contexts, returnValue);
        } else {
            handler.handleAfterThrowing(contexts, throwable);
        }

        handler.handleAfter(contexts, returnValue, throwable);

        if (throwable != null) {
            throw throwable;
        }

        return returnValue;

    }
}
