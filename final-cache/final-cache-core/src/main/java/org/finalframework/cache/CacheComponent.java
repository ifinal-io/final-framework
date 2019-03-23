package org.finalframework.cache;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:09:28
 * @since 1.0
 */
public interface CacheComponent<A extends Annotation, O extends CacheOperation,
        BUILDER extends CacheAnnotationBuilder<A, O>, INVOCATION extends CacheInvocation, HANDLER extends CacheInvocationHandler> {

    @NonNull
    Class<A> annotation();

    @NonNull
    BUILDER builder();

    @NonNull
    INVOCATION invocation();

    @NonNull
    HANDLER handler();

}
