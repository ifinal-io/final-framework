package org.ifinal.finalframework.aop.simple;

import org.ifinal.finalframework.aop.AnnotationFinder;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleAnnotationFinder implements AnnotationFinder<Annotation, Boolean> {

    private final Collection<Class<? extends Annotation>> annotationTypes;

    public SimpleAnnotationFinder(Collection<Class<? extends Annotation>> annotationTypes) {
        this.annotationTypes = annotationTypes;
    }

    @Override
    public Boolean findAnnotations(AnnotatedElement ae) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            if (AnnotatedElementUtils.isAnnotated(ae, annotationType)) {
                return true;
            }
        }


        return false;
    }
}
