package org.finalframework.monitor.action.component;


import org.finalframework.monitor.action.ActionOperation;
import org.finalframework.monitor.action.annotation.OperationAction;
import org.finalframework.monitor.action.builder.OperationActionAnnotationBuilder;
import org.finalframework.monitor.action.handler.ActionInvocationHandler;
import org.finalframework.monitor.action.invocation.ActionOperationInvocation;
import org.finalframework.spring.aop.interceptor.BaseOperationComponent;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 00:02:38
 * @see OperationAction
 * @see OperationActionAnnotationBuilder
 * @see ActionInvocationHandler
 * @see ActionOperationInvocation
 * @since 1.0
 */
public final class ActionOperationComponent extends BaseOperationComponent<OperationAction, ActionOperation,
        OperationActionAnnotationBuilder, ActionInvocationHandler, ActionOperationInvocation> {

    public ActionOperationComponent() {
        super(OperationAction.class, new OperationActionAnnotationBuilder(), new ActionInvocationHandler(), new ActionOperationInvocation());
    }
}
