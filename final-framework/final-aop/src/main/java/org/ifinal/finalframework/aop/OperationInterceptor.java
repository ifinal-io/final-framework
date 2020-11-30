package org.ifinal.finalframework.aop;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ifinal.finalframework.aop.interceptor.BaseOperationInvocation;
import org.ifinal.finalframework.util.Asserts;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class OperationInterceptor implements MethodInterceptor {

    private final OperationConfiguration configuration;

    public OperationInterceptor(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final Object invoke(MethodInvocation invocation) throws Throwable {
        return invoke(new BaseOperationInvocation(configuration, invocation));
    }

    public final Object invoke(OperationInvocation invocation) throws Throwable {
        final Collection<OperationContext> contexts = invocation.getOperationContexts();

        if (Asserts.isEmpty(contexts)) {
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
