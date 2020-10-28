package org.finalframework.aop.interceptor;


import org.finalframework.aop.Operation;
import org.finalframework.aop.OperationAnnotationParser;
import org.finalframework.aop.OperationConfiguration;
import org.finalframework.util.Asserts;

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
