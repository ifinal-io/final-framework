package org.ifinal.finalframework.aop.multi;

import org.ifinal.finalframework.aop.AnnotationFinder;
import org.ifinal.finalframework.aop.single.SingleAnnotationFinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiAnnotationFinder implements AnnotationFinder<Map<Class<? extends Annotation>, Collection<? extends Annotation>>> {

    private final Collection<Class<? extends Annotation>> annotationTypes;

    private final Map<Class<? extends Annotation>, AnnotationFinder<Collection<? extends Annotation>>> finders;

    public MultiAnnotationFinder(Collection<Class<? extends Annotation>> annotationTypes) {
        this.annotationTypes = annotationTypes;
        this.finders = new LinkedHashMap<>(annotationTypes.size());
    }

    @Override
    public Map<Class<? extends Annotation>, Collection<? extends Annotation>> findAnnotations(AnnotatedElement ae) {

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

    private AnnotationFinder<Collection<? extends Annotation>> getAnnotationFinder(Class<? extends Annotation> annotationType) {
        return finders.computeIfAbsent(annotationType, annType -> new SingleAnnotationFinder(annType));
    }
}
