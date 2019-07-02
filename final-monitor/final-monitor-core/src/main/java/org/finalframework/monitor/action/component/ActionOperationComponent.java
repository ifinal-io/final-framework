package org.finalframework.monitor.action.component;


import org.finalframework.monitor.action.ActionOperation;
import org.finalframework.monitor.action.annotation.OperationAction;
import org.finalframework.monitor.action.builder.OperationActionAnnotationBuilder;
import org.finalframework.monitor.action.handler.ActionInvocation;
import org.finalframework.monitor.action.invocation.ActionOperationOperationHandler;
import org.finalframework.spring.aop.interceptor.BaseOperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 00:02:38
 * @see OperationAction
 * @see OperationActionAnnotationBuilder
 * @see ActionInvocation
 * @see ActionOperationOperationHandler
 * @since 1.0
 */
public final class ActionOperationComponent extends BaseOperationComponent<OperationAction, ActionOperation,
        OperationActionAnnotationBuilder, ActionInvocation, ActionOperationOperationHandler> {

    public ActionOperationComponent() {
        super(OperationAction.class, new OperationActionAnnotationBuilder(), new ActionInvocation(), new ActionOperationOperationHandler());
    }
}
