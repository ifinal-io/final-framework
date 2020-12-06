package org.ifinal.finalframework.aop.simple;

import org.ifinal.finalframework.aop.AnnotationFinder;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleAnnotationFinder implements AnnotationFinder<Boolean>, Serializable {

    private static final long serialVersionUID = 4901280051679214659L;
    private final Collection<Class<? extends Annotation>> annotationTypes = new ArrayList<>();

    public SimpleAnnotationFinder(Collection<Class<? extends Annotation>> annotationTypes) {
        this.annotationTypes.addAll(annotationTypes);
    }

    @Override
    public Boolean findAnnotations(@NonNull AnnotatedElement ae) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            if (AnnotatedElementUtils.isAnnotated(ae, annotationType)) {
                return true;
            }
        }

        return false;
    }
}
