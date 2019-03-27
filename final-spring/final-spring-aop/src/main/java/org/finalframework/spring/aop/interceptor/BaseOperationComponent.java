package org.finalframework.spring.aop.interceptor;


import org.finalframework.spring.aop.*;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:51:55
 * @since 1.0
 */
public class BaseOperationComponent<A extends Annotation, O extends Operation,
        BUILDER extends OperationAnnotationBuilder<A, O>, INVOCATION extends Invocation,
        HANDLER extends InvocationHandler> implements OperationComponent<A, O, BUILDER, INVOCATION, HANDLER> {

    private final Class<A> type;
    private final BUILDER builder;
    private final INVOCATION invocation;
    private final HANDLER handler;

    public BaseOperationComponent(Class<A> type, BUILDER builder, INVOCATION invocation, HANDLER handler) {
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
