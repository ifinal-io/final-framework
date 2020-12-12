package org.ifinal.finalframework.context.expression;


import lombok.Getter;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class MethodMetadata {

    private final Method method;
    private final Class<?> returnType;
    private final Type genericReturnType;
    private final Class<?> targetClass;
    private final Method targetMethod;
    private final AnnotatedElementKey methodKey;

    public MethodMetadata(final Method method, final Class<?> targetClass) {

        this.method = BridgeMethodResolver.findBridgedMethod(method);
        this.returnType = method.getReturnType();
        this.genericReturnType = method.getGenericReturnType();
        this.targetClass = targetClass;
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                AopUtils.getMostSpecificMethod(method, targetClass) : this.method);
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
    }
}
