package org.ifinal.finalframework.core.annotation;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.lang.reflect.AnnotatedElement;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface AnnotationAttributesProcessor {

    void doProcess(final @NonNull AnnotatedElement annotatedElement, final @NonNull AnnotationAttributes annotationAttributes);
}
