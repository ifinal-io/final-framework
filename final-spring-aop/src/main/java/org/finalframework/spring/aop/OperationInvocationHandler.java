package org.finalframework.spring.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:10:08
 * @see MethodInvocation#proceed()
 * @since 1.0
 */
public interface OperationInvocationHandler {

    /**
     * @param contexts 操作上下文
     * @return
     * @see OperationHandler#before(Executor, OperationContext)
     */
    @Nullable
    Object handleBefore(@NonNull Collection<OperationContext<Operation>> contexts);

    /**
     * @param contexts
     * @param result
     * @see OperationHandler#afterReturning(Executor, OperationContext, Object)
     */
    default void handleAfterReturning(@NonNull Collection<OperationContext<Operation>> contexts, @Nullable Object result) {
    }

    /**
     * @param contexts
     * @param throwable
     * @see OperationHandler#afterThrowing(Executor, OperationContext, Throwable)
     */
    default void handleAfterThrowing(@NonNull Collection<OperationContext<Operation>> contexts, @NonNull Throwable throwable) {
    }

    /**
     * @param contexts
     * @param result
     * @param throwable
     * @see OperationHandler#after(Executor, OperationContext, Object, Throwable)
     */
    default void handleAfter(@NonNull Collection<OperationContext<Operation>> contexts, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
