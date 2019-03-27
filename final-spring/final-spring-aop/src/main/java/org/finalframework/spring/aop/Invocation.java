package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 09:42:44
 * @since 1.0
 */
public interface Invocation<E extends OperationExecutor, O extends Operation> {

    default Object before(@NonNull E executor, @NonNull OperationContext<O> context, @Nullable Object result) {
        return null;
    }

    default void afterReturning(@NonNull E executor, @NonNull OperationContext<O> context, @Nullable Object result) {
    }

    default void afterThrowing(@NonNull E executor, @NonNull OperationContext<O> context, @NonNull Throwable throwable) {
    }

    default void after(@NonNull E executor, @NonNull OperationContext<O> context, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
