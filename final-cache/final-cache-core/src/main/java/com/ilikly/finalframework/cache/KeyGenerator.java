package com.ilikly.finalframework.cache;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 22:07:46
 * @since 1.0
 */
@FunctionalInterface
public interface KeyGenerator {
    /**
     * Generate a keys for the given method and its parameters.
     *
     * @param target the target instance
     * @param method the method being called
     * @param params the method parameters (with any var-args expanded)
     * @return a generated keys
     */
    Object generate(Object target, Method method, Object... params);
}
