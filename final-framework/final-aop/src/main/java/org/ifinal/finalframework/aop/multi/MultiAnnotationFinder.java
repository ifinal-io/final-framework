package org.ifinal.finalframework.aop.multi;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.ifinal.finalframework.aop.AnnotationFinder;
import org.ifinal.finalframework.aop.single.SingleAnnotationFinder;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiAnnotationFinder implements AnnotationFinder<Map<Class<? extends Annotation>, Collection<? extends Annotation>>>, Serializable {

    private static final long serialVersionUID = -8088506337000616189L;

    private final Collection<Class<? extends Annotation>> annotationTypes;

    private final Map<Class<? extends Annotation>, SingleAnnotationFinder<? extends Annotation>> finders = new LinkedHashMap<>();

    public MultiAnnotationFinder(final Collection<Class<? extends Annotation>> annotationTypes) {

        this.annotationTypes = annotationTypes;
    }

    @Override
    public Map<Class<? extends Annotation>, Collection<? extends Annotation>> findAnnotations(final @NonNull AnnotatedElement ae) {

        Map<Class<? extends Annotation>, Collection<? extends Annotation>> map = new LinkedHashMap<>();

        for (Class<? extends Annotation> annotationType : annotationTypes) {
            Collection<? extends Annotation> annotations = getAnnotationFinder(annotationType).findAnnotations(ae);
            if (annotations.isEmpty()) {
                continue;
            }

            map.put(annotationType, annotations);
        }

        return map;
    }

    private SingleAnnotationFinder<? extends Annotation> getAnnotationFinder(final Class<? extends Annotation> annotationType) {

        return finders.computeIfAbsent(annotationType, SingleAnnotationFinder::new);
    }

}
