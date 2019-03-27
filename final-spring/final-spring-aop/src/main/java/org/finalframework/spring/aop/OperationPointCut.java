package org.finalframework.spring.aop;


import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 00:00:27
 * @since 1.0
 */
public interface OperationPointCut {
    boolean matches(@NonNull Method method, @NonNull Class<?> targetClass);
}
