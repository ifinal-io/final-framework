package org.ifinal.finalframework.aop;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AnnotationBuilder<A extends Annotation, R> {
    @NonNull
    R build(@NonNull Class<?> type, @NonNull A annotation);

    @NonNull
    R build(@NonNull Method method, @NonNull A annotation);

    @NonNull
    R build(@NonNull Parameter parameter, @NonNull Integer index, @NonNull A annotation);
}
