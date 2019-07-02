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

    /**
     * @param contexts
     * @param result
     * @return
     * @see Invocation#before(Executor, OperationContext, Object)
     */
    @Nullable
    Object handleBefore(@NonNull OperationContexts contexts, @Nullable Object result);

    /**
     * @param contexts
     * @param result
     * @see Invocation#afterReturning(Executor, OperationContext, Object)
     */
    default void handleAfterReturning(@NonNull OperationContexts contexts, @Nullable Object result) {
    }

    /**
     * @param contexts
     * @param throwable
     * @see Invocation#afterThrowing(Executor, OperationContext, Throwable)
     */
    default void handleAfterThrowing(@NonNull OperationContexts contexts, @NonNull Throwable throwable) {
    }

    /**
     * @param contexts
     * @param result
     * @param throwable
     * @see Invocation#after(Executor, OperationContext, Object, Throwable)
     */
    default void handleAfter(@NonNull OperationContexts contexts, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
