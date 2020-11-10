package org.finalframework.aop.interceptor;


import org.finalframework.aop.OperationAnnotationFinder;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:46:05
 * @since 1.0
 */
public class BaseOperationAnnotationFinder<A extends Annotation> implements OperationAnnotationFinder<A> {

    private final Class<A> ann;
    private final boolean repeatable;

    @SuppressWarnings("unused")
    private BaseOperationAnnotationFinder(Class<A> ann, boolean repeatable) {
        this.ann = ann;
        this.repeatable = repeatable;
    }

    public BaseOperationAnnotationFinder(Class<A> ann) {
        this.ann = ann;
        this.repeatable = ann.getAnnotation(Repeatable.class) != null;
    }

    @Override
    public Collection<A> findOperationAnnotation(@NonNull Class<?> type) {
        return findOperationAnnotation((AnnotatedElement) type);
    }

    @Override
    public Collection<A> findOperationAnnotation(@NonNull Method method) {
        return findOperationAnnotation((AnnotatedElement) method);
    }

    @Override
    public Collection<A> findOperationAnnotation(@NonNull Parameter parameter) {
        return findOperationAnnotation((AnnotatedElement) parameter);
    }

    private Collection<A> findOperationAnnotation(AnnotatedElement ae) {
        if (repeatable) {
            return AnnotatedElementUtils.findMergedRepeatableAnnotations(ae, ann);
        } else {
            final Collection<A> annotations = AnnotatedElementUtils.findAllMergedAnnotations(ae, ann);
            if (annotations.size() > 1) {
                // More than one annotation found -> local declarations override interface-declared ones...
                return AnnotatedElementUtils.getAllMergedAnnotations(ae, ann);
            }
            return annotations;
        }
    }
}
