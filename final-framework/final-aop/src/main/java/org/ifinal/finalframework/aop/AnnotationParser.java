package org.ifinal.finalframework.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AnnotationParser<R> {

    R parseAnnotations(Class<?> clazz);

    R parseAnnotations(Method method);

    R parseAnnotations(Parameter parameter, Integer index);

}
