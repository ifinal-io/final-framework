package org.ifinal.finalframework.aop;

import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.MethodClassKey;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OperationSource {

    default boolean matches(Method method, @Nullable Class<?> targetClass) {
        return Asserts.nonEmpty(getOperations(method, targetClass));
    }

    @Nullable
    Collection<Operation> getOperations(Method method, @Nullable Class<?> targetClass);

    @NonNull
    default Object getCacheKey(Method method, @Nullable Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }

}
