package org.ifinal.finalframework.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

/**
 * 查找声明在注解元素{@link AnnotatedElement}上的{@link Annotation}, 期中注解元素包含{@link Class}、{@link Method}和{@link Parameter}。
 *
 * @author likly
 * @version 1.0.0
 * @see AnnotatedElementUtils#findMergedRepeatableAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#findAllMergedAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#getAllMergedAnnotations(AnnotatedElement, Class)
 * @since 1.0.0
 */
@FunctionalInterface
public interface AnnotationFinder<A> {

    A findAnnotations(@NonNull AnnotatedElement ae);

}
