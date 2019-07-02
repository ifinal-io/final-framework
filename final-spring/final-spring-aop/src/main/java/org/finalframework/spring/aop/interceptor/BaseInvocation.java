package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.spring.aop.*;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:32:11
 * @since 1.0
 */
public class BaseInvocation<O extends Operation> implements Invocation {

    private final Class<O> type;

    public BaseInvocation(Class<O> type) {
        this.type = type;
    }

    @Override
    public Object handleBefore(OperationContexts contexts, Object result) {
        final Collection<OperationContext<? extends Operation>> operationContexts = contexts.get(type);
        if (Assert.isEmpty(operationContexts)) {
            return null;
        }
        final OperationConfiguration configuration = contexts.configuration();
        for (OperationContext<? extends Operation> context : operationContexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            final Object cacheValue = handler.before(executor, context, result);
            if (cacheValue != null) {
                return cacheValue;
            }
        }
        return null;
    }

    @Override
    public void handleAfterReturning(OperationContexts contexts, Object result) {
        final Collection<OperationContext<? extends Operation>> operationContexts = contexts.get(type);
        if (Assert.isEmpty(operationContexts)) {
            return;
        }
        final OperationConfiguration configuration = contexts.configuration();
        for (OperationContext<? extends Operation> context : operationContexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.afterReturning(executor, context, result);
        }
    }

    @Override
    public void handleAfterThrowing(OperationContexts contexts, Throwable throwable) {
        final Collection<OperationContext<? extends Operation>> operationContexts = contexts.get(type);
        if (Assert.isEmpty(operationContexts)) {
            return;
        }
        final OperationConfiguration configuration = contexts.configuration();
        for (OperationContext<? extends Operation> context : operationContexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.afterThrowing(executor, context, throwable);
        }
    }

    @Override
    public void handleAfter(OperationContexts contexts, Object result, Throwable throwable) {
        final Collection<OperationContext<? extends Operation>> operationContexts = contexts.get(type);
        if (Assert.isEmpty(operationContexts)) {
            return;
        }
        final OperationConfiguration configuration = contexts.configuration();
        for (OperationContext<? extends Operation> context : operationContexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.after(executor, context, result, throwable);
        }
    }
}
