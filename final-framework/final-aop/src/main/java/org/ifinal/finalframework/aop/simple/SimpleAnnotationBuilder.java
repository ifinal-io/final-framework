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
    public A build(final @NonNull Class<?> type, final @NonNull A annotation) {

        return annotation;
    }

    @Override
    @NonNull
    public A build(final @NonNull Method method, final @NonNull A annotation) {

        return annotation;
    }

    @Override
    @NonNull
    public A build(final @NonNull Parameter parameter, final @NonNull Integer index, final @NonNull A annotation) {

        return annotation;
    }
}
