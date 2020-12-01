package org.ifinal.finalframework.aop;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ifinal.finalframework.aop.interceptor.BaseAnnotationMethodInvocation;
import org.ifinal.finalframework.util.Asserts;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationMethodInterceptor implements MethodInterceptor {

    private final OperationConfiguration configuration;

    public AnnotationMethodInterceptor(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final Object invoke(MethodInvocation invocation) throws Throwable {
        return invoke(new BaseAnnotationMethodInvocation(configuration, invocation));
    }

    public final Object invoke(AnnotationMethodInvocation invocation) throws Throwable {
        final Collection<AnnotationInvocationContext> contexts = invocation.getOperationContexts();

        if (Asserts.isEmpty(contexts)) {
            return invocation.proceed();
        }
        final MethodInvocationDispatcher handler = configuration.getInvocationHandler();
        Object operationValue = handler.before(contexts);
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
            handler.afterReturning(contexts, returnValue);
        } else {
            handler.afterThrowing(contexts, throwable);
        }

        handler.after(contexts, returnValue, throwable);

        if (throwable != null) {
            throw throwable;
        }

        return returnValue;

    }
}
