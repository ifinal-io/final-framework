package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationAnnotationParser;
import org.finalframework.spring.aop.OperationConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:01:35
 * @since 1.0
 */
public class AnnotationOperationSource extends AbsOperationSource {

    private final OperationAnnotationParser parser;

    public AnnotationOperationSource(Collection<Class<? extends Annotation>> annoTypes, OperationConfiguration configuration) {
        this.parser = new BaseOperationAnnotationParser(annoTypes, configuration);
    }

    @Override
    protected Collection<? extends Operation> findOperations(Method method) {
        final Collection<? extends Operation> collection = parser.parseOperationAnnotation(method);
        return Asserts.isEmpty(collection) ? null : collection;
    }

    @Override
    protected Collection<? extends Operation> findOperations(Class<?> clazz) {
        final Collection<? extends Operation> collection = parser.parseOperationAnnotation(clazz);
        return Asserts.isEmpty(collection) ? null : collection;
    }
}
