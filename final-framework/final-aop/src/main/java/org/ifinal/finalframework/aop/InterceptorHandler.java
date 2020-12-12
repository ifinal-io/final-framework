package org.ifinal.finalframework.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface InterceptorHandler<E, A> {

    default Object before(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation) {

        handle(executor, context, annotation, null, null);
        return null;
    }

    default void afterReturning(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation, final @Nullable Object result) {

        handle(executor, context, annotation, result, null);
    }


    default void afterThrowing(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation, final @NonNull Throwable throwable) {

        handle(executor, context, annotation, null, throwable);
    }


    default void after(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation, final @Nullable Object result, final @Nullable Throwable throwable) {

        handle(executor, context, annotation, result, throwable);
    }

    default void handle(final @NonNull E executor, final @NonNull InvocationContext context, final @NonNull A annotation, final @Nullable Object result, final @Nullable Throwable throwable) {

    }

}
