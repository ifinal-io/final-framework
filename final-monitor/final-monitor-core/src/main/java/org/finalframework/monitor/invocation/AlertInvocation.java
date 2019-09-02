package org.finalframework.monitor.invocation;

import org.finalframework.monitor.operation.AlertOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-11 10:31
 * @since 1.0
 */
public class AlertInvocation extends BaseInvocation<AlertOperation> {
    public AlertInvocation() {
        super(AlertOperation.class);
    }
}
