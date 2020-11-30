package org.ifinal.finalframework.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OperationHandler<E extends Executor, A extends Annotation> {

    default Object before(@NonNull E executor, @NonNull OperationContext context) {
        return null;
    }


    default void afterReturning(@NonNull E executor, @NonNull OperationContext context, @Nullable Object result) {
    }


    default void afterThrowing(@NonNull E executor, @NonNull OperationContext context, @NonNull Throwable throwable) {
    }


    default void after(@NonNull E executor, @NonNull OperationContext context, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
