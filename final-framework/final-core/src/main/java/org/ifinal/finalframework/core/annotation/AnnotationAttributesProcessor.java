package org.ifinal.finalframework.core.annotation;

import java.lang.reflect.AnnotatedElement;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface AnnotationAttributesProcessor {

    void doProcess(@NonNull AnnotatedElement annotatedElement, @NonNull AnnotationAttributes annotationAttributes);

}
