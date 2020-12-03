package org.ifinal.finalframework.aop.single;


import org.ifinal.finalframework.aop.AnnotationFinder;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SingleAnnotationFinder<A extends Annotation> implements AnnotationFinder<Collection<A>> {

    private final Class<A> ann;
    private final boolean repeatable;

    @SuppressWarnings("unused")
    private SingleAnnotationFinder(Class<A> ann, boolean repeatable) {
        this.ann = ann;
        this.repeatable = repeatable;
    }

    public SingleAnnotationFinder(Class<A> ann) {
        this.ann = ann;
        this.repeatable = ann.getAnnotation(Repeatable.class) != null;
    }

    @Override
    public Collection<A> findAnnotations(AnnotatedElement ae) {
        if (repeatable) {
            return AnnotatedElementUtils.findMergedRepeatableAnnotations(ae, ann);
        } else {
            final Set<A> annotations = AnnotatedElementUtils.findAllMergedAnnotations(ae, ann);
            if (annotations.size() > 1) {
                // More than one annotation found -> local declarations override interface-declared ones...
                return AnnotatedElementUtils.getAllMergedAnnotations(ae, ann);
            }
            return annotations;
        }
    }
}
