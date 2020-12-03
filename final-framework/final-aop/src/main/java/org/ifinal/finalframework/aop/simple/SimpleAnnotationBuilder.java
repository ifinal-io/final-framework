package org.ifinal.finalframework.aop.simple;

import org.ifinal.finalframework.aop.AnnotationBuilder;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SimpleAnnotationBuilder<A extends Annotation> implements AnnotationBuilder<A, A> {
    @Override
    @NonNull
    public A build(@NonNull Class<?> type, @NonNull A annotation) {
        return annotation;
    }

    @Override
    @NonNull
    public A build(@NonNull Method method, @NonNull A annotation) {
        return annotation;
    }

    @Override
    @NonNull
    public A build(@NonNull Parameter parameter, @NonNull Integer index, @NonNull A annotation) {
        return annotation;
    }
}
