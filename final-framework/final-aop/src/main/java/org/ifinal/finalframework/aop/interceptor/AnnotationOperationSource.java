package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.OperationAnnotationParser;
import org.ifinal.finalframework.aop.OperationConfiguration;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationOperationSource extends AbsOperationSource {

    private final OperationAnnotationParser parser;

    public AnnotationOperationSource(Collection<Class<? extends Annotation>> annoTypes, OperationConfiguration configuration) {
        this.parser = new BaseOperationAnnotationParser(annoTypes, configuration);
    }

    @Override
    protected Collection<AnnotationAttributes> findOperations(Method method) {
        final Collection<AnnotationAttributes> collection = parser.parseOperationAnnotation(method);
        return Asserts.isEmpty(collection) ? null : collection;
    }

    @Override
    protected Collection<AnnotationAttributes> findOperations(Class<?> clazz) {
        final Collection<AnnotationAttributes> collection = parser.parseOperationAnnotation(clazz);
        return Asserts.isEmpty(collection) ? null : collection;
    }
}
