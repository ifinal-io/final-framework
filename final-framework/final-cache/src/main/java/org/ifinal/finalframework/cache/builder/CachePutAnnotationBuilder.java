package org.ifinal.finalframework.cache.builder;


import org.ifinal.finalframework.aop.OperationAnnotationBuilder;
import org.ifinal.finalframework.cache.annotation.CachePut;
import org.ifinal.finalframework.cache.operation.CachePutOperation;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CachePutAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<CachePut, CachePutOperation> {

    @Override
    public CachePutOperation build(Class<?> type, CachePut ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CachePutOperation build(Method method, CachePut ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CachePutOperation build(AnnotatedElement ae, CachePut ann) {
        return CachePutOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), ann.delimiter()))
                .field(parse(ann.field(), ann.delimiter()))
                .delimiter(ann.delimiter())
                .value(ann.value())
                .condition(ann.condition())
                .point(ann.point())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();

    }
}
