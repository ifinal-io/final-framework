package org.ifinal.finalframework.aop;

import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JoinPointInterceptorHandler<E, A> extends InterceptorHandler<E, A> {
    @Nullable
    JoinPoint point(final A annotation);

    @Override
    default Object before(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation) {

        if (JoinPoint.BEFORE == point(annotation)) {
            handle(executor, context, annotation, null, null);
        }
        return null;
    }

    @Override
    default void afterReturning(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation, final Object result) {

        if (JoinPoint.AFTER_RETURNING == point(annotation)) {
            handle(executor, context, annotation, result, null);
        }
    }

    @Override
    default void afterThrowing(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation, final @NonNull Throwable throwable) {

        if (JoinPoint.AFTER_THROWING == point(annotation)) {
            handle(executor, context, annotation, null, throwable);
        }
    }

    @Override
    default void after(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation, final Object result, final Throwable throwable) {

        if (JoinPoint.AFTER == point(annotation)) {
            handle(executor, context, annotation, result, throwable);
        }
    }
}
