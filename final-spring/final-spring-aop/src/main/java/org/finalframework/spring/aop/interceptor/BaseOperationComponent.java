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
        BUILDER extends OperationAnnotationBuilder<A, O>, HANDLER extends Invocation, INVOCATION extends OperationHandler
        > implements OperationComponent<A, O, BUILDER, HANDLER, INVOCATION> {

    private final Class<A> type;
    private final BUILDER builder;
    private final HANDLER handler;
    private final INVOCATION invocation;

    public BaseOperationComponent(Class<A> type, BUILDER builder, HANDLER handler, INVOCATION invocation) {
        this.type = type;
        this.builder = builder;
        this.handler = handler;
        this.invocation = invocation;
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
    public HANDLER handler() {
        return handler;
    }

    @Override
    public INVOCATION invocation() {
        return invocation;
    }
}
