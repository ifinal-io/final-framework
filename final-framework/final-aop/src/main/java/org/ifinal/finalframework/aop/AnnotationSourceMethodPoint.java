package org.ifinal.finalframework.aop;

import org.ifinal.finalframework.util.Asserts;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationSourceMethodPoint extends StaticMethodMatcherPointcut {

    private final AnnotationSource<?> source;

    public AnnotationSourceMethodPoint(AnnotationSource<?> source) {
        this.source = source;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return Asserts.nonEmpty(source.getAnnotations(method, targetClass));
    }
}
