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
public class BaseMethodInvocationDispatcher implements MethodInvocationDispatcher {

    private static final String EXECUTOR = "executor";

    private final OperationConfiguration configuration;

    public BaseMethodInvocationDispatcher(@NonNull OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object before(Collection<AnnotationInvocationContext> contexts) {
        for (AnnotationInvocationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationAttributes().getClass("handler"));
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            final Object cacheValue = handler.before(executor, context);
            if (cacheValue != null) {
                return cacheValue;
            }
        }
        return null;
    }

    @Override
    public void afterReturning(Collection<AnnotationInvocationContext> contexts, Object result) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (AnnotationInvocationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationAttributes().getClass("handler"));
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            handler.afterReturning(executor, context, result);
        }
    }

    @Override
    public void afterThrowing(Collection<AnnotationInvocationContext> contexts, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (AnnotationInvocationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationAttributes().getClass("handler"));
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            handler.afterThrowing(executor, context, throwable);
        }
    }

    @Override
    public void after(Collection<AnnotationInvocationContext> contexts, Object result, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (AnnotationInvocationContext context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.annotationAttributes().getClass("handler"));
            final Executor executor = configuration.getExecutor(context.annotationAttributes().getClass(EXECUTOR));
            handler.after(executor, context, result, throwable);
        }
    }
}
