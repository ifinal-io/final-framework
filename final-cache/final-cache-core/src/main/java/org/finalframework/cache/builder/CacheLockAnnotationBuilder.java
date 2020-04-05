package org.finalframework.cache.builder;


import org.finalframework.cache.annotation.CacheLock;
import org.finalframework.cache.operation.CacheLockOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationAnnotationBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:26:21
 * @since 1.0
 */
@SpringComponent
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
