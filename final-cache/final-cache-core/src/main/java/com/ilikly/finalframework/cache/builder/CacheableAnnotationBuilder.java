package com.ilikly.finalframework.cache.builder;


import com.ilikly.finalframework.cache.CacheAnnotationBuilder;
import com.ilikly.finalframework.cache.annotation.Cacheable;
import com.ilikly.finalframework.cache.operation.CacheableOperation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:26:21
 * @since 1.0
 */
public class CacheableAnnotationBuilder extends AbsCacheAnnotationBuilder implements CacheAnnotationBuilder<Cacheable, CacheableOperation> {

    @Override
    public CacheableOperation build(Class<?> type, Cacheable ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheableOperation build(Method method, Cacheable ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheableOperation build(AnnotatedElement ae, Cacheable ann) {
        return CacheableOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), ann.delimiter()))
                .field(parse(ann.field(), ann.delimiter()))
                .delimiter(ann.delimiter())
                .condition(ann.condition())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .invocation(ann.invocation())
                .build();

    }
}
