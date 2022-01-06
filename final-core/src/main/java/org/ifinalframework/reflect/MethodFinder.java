package org.ifinalframework.reflect;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.2.4
 **/
@FunctionalInterface
public interface MethodFinder {
    @Nullable
    Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments);
}
