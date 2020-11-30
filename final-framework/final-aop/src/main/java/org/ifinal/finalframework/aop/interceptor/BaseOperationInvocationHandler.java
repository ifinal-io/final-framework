package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.*;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseOperationInvocationHandler implements OperationInvocationHandler {

    private static final String EXECUTOR = "executor";

    private final OperationConfiguration configuration;

    public BaseOperationInvocationHandler(@NonNull OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object handleBefore(Collection<OperationContext> contexts) {
        for (OperationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationType());
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            final Object cacheValue = handler.before(executor, context);
            if (cacheValue != null) {
                return cacheValue;
            }
        }
        return null;
    }

    @Override
    public void handleAfterReturning(Collection<OperationContext> contexts, Object result) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationType());
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            handler.afterReturning(executor, context, result);
        }
    }

    @Override
    public void handleAfterThrowing(Collection<OperationContext> contexts, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationType());
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            handler.afterThrowing(executor, context, throwable);
        }
    }

    @Override
    public void handleAfter(Collection<OperationContext> contexts, Object result, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationType());
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            handler.after(executor, context, result, throwable);
        }
    }
}
