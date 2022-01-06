package org.ifinalframework.reflect;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.2.4
 **/
@FunctionalInterface
public interface MethodInvoker {
    @Nullable
    Object invoke(@NonNull Method method,@NonNull Object target,@Nullable Object... args);
}
