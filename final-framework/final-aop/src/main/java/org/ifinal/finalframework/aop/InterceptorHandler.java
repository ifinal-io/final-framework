package org.ifinal.finalframework.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface InterceptorHandler<E, A> {

    Object before(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation);

    void afterReturning(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, @Nullable Object result);


    void afterThrowing(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, @NonNull Throwable throwable);


    void after(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, @Nullable Object result, @Nullable Throwable throwable);

}
