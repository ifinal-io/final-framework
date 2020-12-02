package org.ifinal.finalframework.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @see MethodInvocation#proceed()
 * @since 1.0.0
 */
public interface MethodInvocationDispatcher<A> {

    @Nullable
    Object before(@NonNull InvocationContext context, @NonNull A annotations);

    default void afterReturning(@NonNull InvocationContext context, @NonNull A annotations, @Nullable Object result) {
    }

    default void afterThrowing(@NonNull InvocationContext context, @NonNull A annotations, @NonNull Throwable throwable) {
    }

    default void after(@NonNull InvocationContext context, @NonNull A annotations, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
