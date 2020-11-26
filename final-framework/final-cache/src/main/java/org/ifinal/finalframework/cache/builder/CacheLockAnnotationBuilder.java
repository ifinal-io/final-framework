package org.ifinal.finalframework.cache.builder;


import org.ifinal.finalframework.aop.OperationAnnotationBuilder;
import org.ifinal.finalframework.cache.annotation.CacheLock;
import org.ifinal.finalframework.cache.operation.CacheLockOperation;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheLockAnnotationBuilder extends AbsCacheAnnotationBuilder implements OperationAnnotationBuilder<CacheLock, CacheLockOperation> {

    @Override
    public CacheLockOperation build(Class<?> type, CacheLock ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheLockOperation build(Method method, CacheLock ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheLockOperation build(AnnotatedElement ae, CacheLock ann) {
        return CacheLockOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), ann.delimiter()))
                .value(ann.value())
                .delimiter(ann.delimiter())
                .condition(ann.condition())
                .retry(ann.retry())
                .sleep(ann.sleep())
                .ttl(ann.ttl())
                .timeunit(ann.timeunit())
                .handler(ann.handler())
                .executor(ann.executor())
                .build();

    }
}
