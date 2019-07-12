package org.finalframework.monitor.component;


import org.finalframework.monitor.annotation.OperationAction;
import org.finalframework.monitor.builder.ActionOperationAnnotationBuilder;
import org.finalframework.monitor.handler.ActionOperationHandler;
import org.finalframework.monitor.invocation.ActionInvocation;
import org.finalframework.spring.aop.annotation.OperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 00:02:38
 * @see OperationAction
 * @see ActionOperationAnnotationBuilder
 * @see ActionInvocation
 * @see ActionOperationHandler
 * @since 1.0
 */
@OperationComponent(
        annotation = OperationAction.class,
        builder = ActionOperationAnnotationBuilder.class,
        handler = ActionOperationHandler.class,
        invocation = ActionInvocation.class
)
public final class ActionOperationComponent {
}