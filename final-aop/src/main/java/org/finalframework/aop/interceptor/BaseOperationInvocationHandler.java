package org.finalframework.aop.interceptor;


import org.finalframework.aop.*;
import org.finalframework.util.Asserts;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:32:11
 * @since 1.0
 */
public class BaseOperationInvocationHandler implements OperationInvocationHandler {

    @NonNull
    private final OperationConfiguration configuration;

    public BaseOperationInvocationHandler(@NonNull OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object handleBefore(Collection<OperationContext<Operation>> contexts) {
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            final Object cacheValue = handler.before(executor, context);
            if (cacheValue != null) {
                return cacheValue;
            }
        }
        return null;
    }

    @Override
    public void handleAfterReturning(Collection<OperationContext<Operation>> contexts, Object result) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.afterReturning(executor, context, result);
        }
    }

    @Override
    public void handleAfterThrowing(Collection<OperationContext<Operation>> contexts, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.afterThrowing(executor, context, throwable);
        }
    }

    @Override
    public void handleAfter(Collection<OperationContext<Operation>> contexts, Object result, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.after(executor, context, result, throwable);
        }
    }
}
