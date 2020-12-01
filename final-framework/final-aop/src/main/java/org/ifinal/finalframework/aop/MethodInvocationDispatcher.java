package org.ifinal.finalframework.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @see MethodInvocation#proceed()
 * @since 1.0.0
 */
public interface MethodInvocationDispatcher {

    /**
     * @param contexts 操作上下文
     * @return
     * @see OperationHandler#before(Executor, AnnotationInvocationContext)
     */
    @Nullable
    Object before(@NonNull Collection<AnnotationInvocationContext> contexts);

    /**
     * @param contexts
     * @param result
     * @see OperationHandler#afterReturning(Executor, AnnotationInvocationContext, Object)
     */
    default void afterReturning(@NonNull Collection<AnnotationInvocationContext> contexts, @Nullable Object result) {
    }

    /**
     * @param contexts
     * @param throwable
     * @see OperationHandler#afterThrowing(Executor, AnnotationInvocationContext, Throwable)
     */
    default void afterThrowing(@NonNull Collection<AnnotationInvocationContext> contexts, @NonNull Throwable throwable) {
    }

    /**
     * @param contexts
     * @param result
     * @param throwable
     * @see OperationHandler#after(Executor, AnnotationInvocationContext, Object, Throwable)
     */
    default void after(@NonNull Collection<AnnotationInvocationContext> contexts, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
