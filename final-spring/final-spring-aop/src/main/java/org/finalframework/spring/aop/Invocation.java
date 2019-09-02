package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:10:08
 * @since 1.0
 */
public interface Invocation {

    /**
     * @param contexts 操作上下文
     * @return
     * @see OperationHandler#before(Executor, OperationContext)
     */
    @Nullable
    Object handleBefore(@NonNull OperationContexts contexts);

    /**
     * @param contexts
     * @param result
     * @see OperationHandler#afterReturning(Executor, OperationContext, Object)
     */
    default void handleAfterReturning(@NonNull OperationContexts contexts, @Nullable Object result) {
    }

    /**
     * @param contexts
     * @param throwable
     * @see OperationHandler#afterThrowing(Executor, OperationContext, Throwable)
     */
    default void handleAfterThrowing(@NonNull OperationContexts contexts, @NonNull Throwable throwable) {
    }

    /**
     * @param contexts
     * @param result
     * @param throwable
     * @see OperationHandler#after(Executor, OperationContext, Object, Throwable)
     */
    default void handleAfter(@NonNull OperationContexts contexts, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
