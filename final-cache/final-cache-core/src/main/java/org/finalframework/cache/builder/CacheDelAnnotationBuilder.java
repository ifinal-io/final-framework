package org.finalframework.cache.builder;


import org.finalframework.cache.CacheAnnotationBuilder;
import org.finalframework.cache.annotation.CacheDel;
import org.finalframework.cache.operation.CacheDelOperation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:26:21
 * @since 1.0
 */
public class CacheDelAnnotationBuilder extends AbsCacheAnnotationBuilder implements CacheAnnotationBuilder<CacheDel, CacheDelOperation> {

    @Override
    public CacheDelOperation build(Class<?> type, CacheDel ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheDelOperation build(Method method, CacheDel ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheDelOperation build(AnnotatedElement ae, CacheDel ann) {
        return CacheDelOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), ann.delimiter()))
                .field(parse(ann.field(), ann.delimiter()))
                .delimiter(ann.delimiter())
                .condition(ann.condition())
                .retry(ann.retry())
                .sleep(ann.sleep())
                .invocation(ann.invocation())
                .build();

    }
}
