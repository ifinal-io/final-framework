

package org.finalframework.spring.aop;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * 查找声明在注解元素{@link AnnotatedElement}上的{@link Annotation},
 * 期中注解元素包含{@link Class}、{@link Method}和{@link Parameter}。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:43:08
 * @see AnnotatedElementUtils#findMergedRepeatableAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#findAllMergedAnnotations(AnnotatedElement, Class)
 * @see AnnotatedElementUtils#getAllMergedAnnotations(AnnotatedElement, Class)
 * @since 1.0
 */
public interface OperationAnnotationFinder<A extends Annotation> {

    /**
     * 查询声明在{@link Class}上的注解元素{@link A}
     *
     * @param type 类
     */
    @Nullable
    Collection<A> findOperationAnnotation(@NonNull Class<?> type);

    /**
     * 查询声明在{@link Method}上的注解元素{@link A}
     *
     * @param method 方法
     */
    @Nullable
    Collection<A> findOperationAnnotation(@NonNull Method method);

    /**
     * 查询声明在{@link Parameter}上的注解元素{@link A}
     *
     * @param parameter 方法参数
     */
    @Nullable
    Collection<A> findOperationAnnotation(@NonNull Parameter parameter);

}
