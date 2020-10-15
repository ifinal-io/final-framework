package org.finalframework.cache.builder;


import org.finalframework.aop.OperationAnnotationBuilder;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.cache.annotation.CachePut;
import org.finalframework.cache.operation.CachePutOperation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:26:21
 * @since 1.0
 */
@SpringComponent
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
