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
public class BaseInvocationHandler<O extends Operation> implements InvocationHandler {

    private final Class<O> type;

    public BaseInvocationHandler(Class<O> type) {
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
            final Invocation invocation = configuration.getInvocation(context.operation().invocation());
            final Executor executor = configuration.getExecutor(context.operation());
            final Object cacheValue = invocation.before(executor, context, result);
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
            final Invocation invocation = configuration.getInvocation(context.operation().invocation());
            final Executor executor = configuration.getExecutor(context.operation());
            invocation.afterReturning(executor, context, result);
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
            final Invocation invocation = configuration.getInvocation(context.operation().invocation());
            final Executor executor = configuration.getExecutor(context.operation());
            invocation.afterThrowing(executor, context, throwable);
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
            final Invocation invocation = configuration.getInvocation(context.operation().invocation());
            final Executor executor = configuration.getExecutor(context.operation());
            invocation.after(executor, context, result, throwable);
        }
    }
}
