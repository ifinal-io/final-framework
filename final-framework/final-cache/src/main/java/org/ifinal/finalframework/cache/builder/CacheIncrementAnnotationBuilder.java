package org.ifinal.finalframework.cache.builder;


import org.ifinal.finalframework.aop.OperationAnnotationBuilder;
import org.ifinal.finalframework.cache.annotation.CacheIncrement;
import org.ifinal.finalframework.cache.operation.CacheIncrementOperation;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheIncrementAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<CacheIncrement, CacheIncrementOperation> {

    @Override
    public CacheIncrementOperation build(Class<?> type, CacheIncrement ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheIncrementOperation build(Method method, CacheIncrement ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheIncrementOperation build(AnnotatedElement ae, CacheIncrement ann) {
        final String delimiter = getDelimiter(ann.delimiter());
        return CacheIncrementOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), delimiter))
                .field(parse(ann.field(), delimiter))
                .delimiter(delimiter)
                .condition(ann.condition())
                .point(ann.point())
                .value(ann.value())
                .type(ann.type())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();

    }
}
