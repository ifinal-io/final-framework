package org.ifinal.finalframework.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationAttributesAnnotationBuilder<A extends Annotation> implements
    AnnotationBuilder<A, AnnotationAttributes> {

    @Override
    @NonNull
    public AnnotationAttributes build(final @NonNull Class<?> type, final @NonNull A annotation) {

        final AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(type, annotation);
        annotationAttributes.put("class", type);
        return annotationAttributes;
    }

    @Override
    @NonNull
    public AnnotationAttributes build(final @NonNull Method method, final @NonNull A annotation) {

        final AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(method, annotation);
        annotationAttributes.put("method", method);
        return annotationAttributes;
    }

    @Override
    @NonNull
    public AnnotationAttributes build(final @NonNull Parameter parameter, final @NonNull Integer index,
        final @NonNull A annotation) {

        final AnnotationAttributes annotationAttributes
            = AnnotationUtils.getAnnotationAttributes(parameter, annotation);
        annotationAttributes.put("parameter", parameter);
        annotationAttributes.put("parameterIndex", index);
        return annotationAttributes;
    }

}
