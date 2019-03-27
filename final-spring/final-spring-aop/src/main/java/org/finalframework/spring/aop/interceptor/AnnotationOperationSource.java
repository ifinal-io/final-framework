package org.finalframework.spring.aop.interceptor;


import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationAnnotationBuilder;
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
public class AnnotationOperationSource<O extends Operation, OAB extends OperationAnnotationBuilder<Annotation, O>> extends AbsOperationSource<O> {

    private final OperationAnnotationParser<O> operationAnnotationParser;

    public AnnotationOperationSource(OperationConfiguration configuration) {
        this.operationAnnotationParser = new BaseOperationAnnotationParser<>(configuration);
    }

    @Override
    protected Collection<O> findOperations(Method method) {
        return operationAnnotationParser.parseOperationAnnotation(method);
    }

    @Override
    protected Collection<O> findOperations(Class<?> clazz) {
        return operationAnnotationParser.parseOperationAnnotation(clazz);
    }
}
