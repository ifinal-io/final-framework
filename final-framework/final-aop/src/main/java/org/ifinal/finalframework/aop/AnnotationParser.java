package org.ifinal.finalframework.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AnnotationParser<R> {

    R parseAnnotations(final Class<?> clazz);

    R parseAnnotations(final Method method);

    R parseAnnotations(final Parameter parameter, final Integer index);

}
