package org.finalframework.monitor.invocation;


import org.finalframework.monitor.operation.ActionOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 00:01:21
 * @since 1.0
 */
public class ActionInvocation extends BaseInvocation<ActionOperation> {

    public ActionInvocation() {
        super(ActionOperation.class);
    }
}
