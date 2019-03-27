package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:43:08
 * @since 1.0
 */
public interface OperationAnnotationFinder<A extends Annotation> {

    Collection<A> findOperationAnnotation(@NonNull Class<?> type);

    Collection<A> findOperationAnnotation(@NonNull Method method);

    Collection<A> findOperationAnnotation(@NonNull Parameter parameter);

}
