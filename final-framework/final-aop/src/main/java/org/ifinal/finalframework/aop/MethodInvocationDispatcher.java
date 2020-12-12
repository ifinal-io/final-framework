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
    Object before(final @NonNull InvocationContext context, final @NonNull A annotations);

    default void afterReturning(final @NonNull InvocationContext context, final @NonNull A annotations, final @Nullable Object result) {

    }

    default void afterThrowing(final @NonNull InvocationContext context, final @NonNull A annotations, final @NonNull Throwable throwable) {

    }

    default void after(final @NonNull InvocationContext context, final @NonNull A annotations, final @Nullable Object result, final @Nullable Throwable throwable) {

    }

}
