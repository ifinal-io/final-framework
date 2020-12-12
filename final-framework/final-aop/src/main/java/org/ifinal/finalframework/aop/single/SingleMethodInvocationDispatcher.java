package org.ifinal.finalframework.aop.single;

import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.MethodInvocationDispatcher;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SingleMethodInvocationDispatcher<E, A> implements MethodInvocationDispatcher<Collection<A>> {

    private final List<InterceptorHandler<E, A>> handlers;

    protected SingleMethodInvocationDispatcher(final List<InterceptorHandler<E, A>> handlers) {

        this.handlers = handlers;
    }

    @Override
    public Object before(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                E handlerExecutor = getExecutor(annotation);
                Object value = handler.before(handlerExecutor, context, annotation);
                if (Objects.nonNull(value)) {
                    return value;
                }
            }
        }

        return null;
    }

    @Override
    public void afterReturning(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations, final Object result) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                E handlerExecutor = getExecutor(annotation);
                handler.afterReturning(handlerExecutor, context, annotation, result);
            }
        }
    }

    @Override
    public void afterThrowing(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations, final @NonNull Throwable throwable) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                E handlerExecutor = getExecutor(annotation);
                handler.afterThrowing(handlerExecutor, context, annotation, throwable);
            }
        }
    }

    @Override
    public void after(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations, final Object result, final Throwable throwable) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                E handlerExecutor = getExecutor(annotation);
                handler.after(handlerExecutor, context, annotation, result, throwable);
            }
        }
    }

    @NonNull
    protected abstract E getExecutor(final A annotation);
}
