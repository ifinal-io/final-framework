package org.finalframework.cache.builder;


import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.operation.CacheableOperation;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:26:21
 * @since 1.0
 */
public class CacheableAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<Cacheable, CacheableOperation> {

    @Override
    public CacheableOperation build(Class<?> type, Cacheable ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheableOperation build(Method method, Cacheable ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheableOperation build(AnnotatedElement ae, Cacheable ann) {
        final String delimiter = getDelimiter(ann.delimiter());
        return CacheableOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), delimiter))
                .field(parse(ann.field(), delimiter))
                .delimiter(delimiter)
                .condition(ann.condition())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .invocation(ann.invocation())
                .build();

    }
}