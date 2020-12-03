package org.ifinal.finalframework.aop.simple;

import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.MethodInvocationDispatcher;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleMethodInvocationDispatcher<T> implements MethodInvocationDispatcher<Boolean> {
    private final List<InterceptorHandler<T, Boolean>> handlers;

    public SimpleMethodInvocationDispatcher(List<InterceptorHandler<T, Boolean>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Object before(@NonNull InvocationContext context, @NonNull Boolean annotations) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            T executor = getExecutor();
            Object value = handler.before(executor, context, annotations);
            if (Objects.nonNull(value)) {
                return value;
            }
        }

        return null;
    }


    @Override
    public void afterReturning(InvocationContext context, Boolean annotations, Object result) {
        for (InterceptorHandler<T, Boolean> handler : handlers) {
            T executor = getExecutor();
            handler.afterReturning(executor, context, annotations, result);
        }
    }

    @Override
    public void afterThrowing(InvocationContext context, Boolean annotations, Throwable throwable) {
        for (InterceptorHandler<T, Boolean> handler : handlers) {
            T executor = getExecutor();
            handler.afterThrowing(executor, context, annotations, throwable);
        }
    }

    @Override
    public void after(InvocationContext context, Boolean annotations, Object result, Throwable throwable) {
        for (InterceptorHandler<T, Boolean> handler : handlers) {
            T executor = getExecutor();
            handler.after(executor, context, annotations, result, throwable);
        }
    }

    @NonNull
    protected abstract T getExecutor();
}
