package org.ifinal.finalframework.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AnnotationBuilder<A extends Annotation, R> {

    @NonNull
    R build(final @NonNull Class<?> type, final @NonNull A annotation);

    @NonNull
    R build(final @NonNull Method method, final @NonNull A annotation);

    @NonNull
    R build(final @NonNull Parameter parameter, final @NonNull Integer index, final @NonNull A annotation);

}
