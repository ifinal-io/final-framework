package org.ifinal.finalframework.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface InterceptorHandler<E, A> {

    default Object before(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation) {
        handle(executor, context, annotation, null, null);
        return null;
    }

    default void afterReturning(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, @Nullable Object result) {
        handle(executor, context, annotation, result, null);
    }


    default void afterThrowing(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, @NonNull Throwable throwable) {
        handle(executor, context, annotation, null, throwable);
    }


    default void after(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, @Nullable Object result, @Nullable Throwable throwable) {
        handle(executor, context, annotation, result, throwable);
    }

    default void handle(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, @Nullable Object result, @Nullable Throwable throwable) {

    }

}
