package org.ifinal.finalframework.aop.simple;

import java.util.List;
import java.util.Objects;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.MethodInvocationDispatcher;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleMethodInvocationDispatcher<T> implements MethodInvocationDispatcher<Boolean> {

    private final List<InterceptorHandler<T, Boolean>> handlers;

    protected SimpleMethodInvocationDispatcher(final List<InterceptorHandler<T, Boolean>> handlers) {

        this.handlers = handlers;
    }

    @Override
    public Object before(final @NonNull InvocationContext context, final @NonNull Boolean annotations) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            final Object value = handler.before(executor, context, annotations);
            if (Objects.nonNull(value)) {
                return value;
            }
        }

        return null;
    }

    @Override
    public void afterReturning(final @NonNull InvocationContext context, final @NonNull Boolean annotations,
        final Object result) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            handler.afterReturning(executor, context, annotations, result);
        }
    }

    @Override
    public void afterThrowing(final @NonNull InvocationContext context, final @NonNull Boolean annotations,
        final Throwable throwable) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            handler.afterThrowing(executor, context, annotations, throwable);
        }
    }

    @Override
    public void after(final @NonNull InvocationContext context, final @NonNull Boolean annotations, final Object result,
        final Throwable throwable) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            handler.after(executor, context, annotations, result, throwable);
        }
    }

    @NonNull
    protected abstract T getExecutor();

}
