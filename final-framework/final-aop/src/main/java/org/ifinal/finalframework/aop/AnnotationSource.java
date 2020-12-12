package org.ifinal.finalframework.aop;

import org.springframework.core.MethodClassKey;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface AnnotationSource<R> {

    @Nullable
    R getAnnotations(final Method method, final @Nullable Class<?> targetClass);

    @NonNull
    default Object getCacheKey(final Method method, final @Nullable Class<?> targetClass) {

        return new MethodClassKey(method, targetClass);
    }

}
