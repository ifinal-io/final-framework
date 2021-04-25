package org.ifinal.finalframework.core.annotation;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.lang.reflect.AnnotatedElement;

/**
 * AnnotationAttributesProcessor.
 *
 * @author likly
 * @version 1.0.0
 * @see AnnotationAttributes
 * @since 1.0.0
 */
@FunctionalInterface
public interface AnnotationAttributesProcessor2 {

    void doProcess(@NonNull AnnotatedElement annotatedElement, @NonNull AnnotationAttributes annotationAttributes);

}
