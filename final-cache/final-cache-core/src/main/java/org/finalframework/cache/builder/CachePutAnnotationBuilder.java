package org.finalframework.cache.builder;


import org.finalframework.cache.CacheAnnotationBuilder;
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
public class CachePutAnnotationBuilder extends AbsCacheAnnotationBuilder implements CacheAnnotationBuilder<CachePut, CachePutOperation> {

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
                .condition(ann.condition())
                .order(ann.order())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .invocation(ann.invocation())
                .build();

    }
}
