package org.finalframework.cache.component;


import org.finalframework.cache.*;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:14:22
 * @since 1.0
 */
public class AbsCacheComponent<A extends Annotation, O extends CacheOperation<A>,
        BUILDER extends CacheAnnotationBuilder<A, O>, INVOCATION extends CacheInvocation,
        HANDLER extends CacheInvocationHandler> implements CacheComponent<A, O, BUILDER, INVOCATION, HANDLER> {

    private final Class<A> type;
    private final BUILDER builder;
    private final INVOCATION invocation;
    private final HANDLER handler;

    public AbsCacheComponent(Class<A> type, BUILDER builder, INVOCATION invocation, HANDLER handler) {
        this.type = type;
        this.builder = builder;
        this.invocation = invocation;
        this.handler = handler;
    }

    @Override
    public Class<A> annotation() {
        return type;
    }

    @Override
    public BUILDER builder() {
        return builder;
    }

    @Override
    public INVOCATION invocation() {
        return invocation;
    }

    @Override
    public HANDLER handler() {
        return handler;
    }
}
