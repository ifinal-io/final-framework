package org.ifinal.finalframework.aop;


import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationMethodMatcherPointcut extends StaticMethodMatcherPointcut {
    private final OperationSource source;

    public AnnotationMethodMatcherPointcut(OperationSource source) {
        this.source = source;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return source != null && source.matches(method, targetClass);
    }
}

