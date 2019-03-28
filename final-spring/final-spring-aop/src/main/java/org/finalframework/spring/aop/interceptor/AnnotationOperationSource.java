package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationAnnotationParser;
import org.finalframework.spring.aop.OperationConfiguration;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:01:35
 * @since 1.0
 */
public class AnnotationOperationSource<O extends Operation> extends AbsOperationSource<O> {

    private final OperationAnnotationParser<O> operationAnnotationParser;

    public AnnotationOperationSource(OperationConfiguration configuration) {
        this.operationAnnotationParser = new BaseOperationAnnotationParser<>(configuration);
    }

    @Override
    protected Collection<O> findOperations(Method method) {
        final Collection<O> collection = operationAnnotationParser.parseOperationAnnotation(method);
        return Assert.isEmpty(collection) ? null : collection;
    }

    @Override
    protected Collection<O> findOperations(Class<?> clazz) {
        final Collection<O> collection = operationAnnotationParser.parseOperationAnnotation(clazz);
        return Assert.isEmpty(collection) ? null : collection;
    }
}
