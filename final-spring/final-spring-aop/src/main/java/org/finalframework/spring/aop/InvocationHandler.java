package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:10:08
 * @since 1.0
 */
public interface InvocationHandler {

    @Nullable
    Object handleBefore(@NonNull OperationContexts contexts, @Nullable Object result);

    default void handleAfterReturning(@NonNull OperationContexts contexts, @Nullable Object result) {
    }

    default void handleAfterThrowing(@NonNull OperationContexts contexts, @NonNull Throwable throwable) {
    }

    default void handleAfter(@NonNull OperationContexts contexts, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
