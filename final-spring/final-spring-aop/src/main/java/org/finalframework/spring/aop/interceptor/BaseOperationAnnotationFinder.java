package org.finalframework.spring.aop.interceptor;


import org.finalframework.spring.aop.OperationAnnotationFinder;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:46:05
 * @since 1.0
 */
public class BaseOperationAnnotationFinder<A extends Annotation> implements OperationAnnotationFinder<A> {

    private final Class<A> ann;
    private final boolean repeatable;

    private BaseOperationAnnotationFinder(Class<A> ann, boolean repeatable) {
        this.ann = ann;
        this.repeatable = repeatable;
    }

    public BaseOperationAnnotationFinder(Class<A> ann) {
        this.ann = ann;
        this.repeatable = ann.getAnnotation(Repeatable.class) != null;
    }

    @Override
    public Collection<A> findOperationAnnotation(Class<?> type) {
        return findOperationAnnotation((AnnotatedElement) type);
    }

    @Override
    public Collection<A> findOperationAnnotation(Method method) {
        return findOperationAnnotation((AnnotatedElement) method);
    }

    @Override
    public Collection<A> findOperationAnnotation(Parameter parameter) {
        return findOperationAnnotation((AnnotatedElement) parameter);
    }

    @SuppressWarnings("unchecked")
    private Collection<A> findOperationAnnotation(AnnotatedElement ae) {
        final Set<Class<? extends Annotation>> annTypes = Collections.singleton(ann);
        final Collection<Annotation> annotations = AnnotatedElementUtils.getAllMergedAnnotations(ae, annTypes);

        /**
         * 	public static Set<Annotation> getAllMergedAnnotations(AnnotatedElement element, Set<Class<? extends Annotation>> annotationTypes) {
         * 		MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
         * 		searchWithGetSemantics(element, annotationTypes, null, null, processor);
         * 		return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
         *  }
         *
         *  	public static Set<Annotation> findAllMergedAnnotations(AnnotatedElement element, Set<Class<? extends Annotation>> annotationTypes) {
         * 		MergedAnnotationAttributesProcessor processor = new MergedAnnotationAttributesProcessor(false, false, true);
         * 		searchWithFindSemantics(element, annotationTypes, null, null, processor);
         * 		return postProcessAndSynthesizeAggregatedResults(element, processor.getAggregatedResults());
         *        }
         */

        if (annotations.size() > 1 && !repeatable) {
            // More than one annotation found -> local declarations override interface-declared ones...
            return AnnotatedElementUtils.findAllMergedAnnotations(ae, ann);
        } else if (annotations.isEmpty()) {
            return AnnotatedElementUtils.findAllMergedAnnotations(ae, ann);
        }

        return (Collection<A>) annotations;
    }
}