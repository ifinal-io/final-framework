package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:50:40
 * @since 1.0
 */
public interface OperationComponent<A extends Annotation, O extends Operation,
        BUILDER extends OperationAnnotationBuilder<A, O>, HANDLER extends InvocationHandler, INVOCATION extends Invocation> {


    @NonNull
    Class<A> annotation();

    @NonNull
    BUILDER builder();

    @NonNull
    HANDLER handler();

    @NonNull
    INVOCATION invocation();
}
