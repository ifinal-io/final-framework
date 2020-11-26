package org.ifinal.finalframework.context.expression;


import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class MethodExpressionRootObject {
    private final Method method;
    private final Object[] args;
    private final Object target;
    private final Class<?> targetClass;

    public MethodExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {
        this.method = method;
        this.args = args;
        this.target = target;
        this.targetClass = targetClass;
    }
}
