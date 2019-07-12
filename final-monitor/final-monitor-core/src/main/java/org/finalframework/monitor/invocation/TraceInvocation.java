package org.finalframework.monitor.invocation;

import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.aop.interceptor.BaseInvocation;

/**
 * @author ilikly
 * @version 1.0
 * @date 2019-07-09 17:46
 * @since 1.0
 */
public class TraceInvocation extends BaseInvocation<TraceOperation> {
    public TraceInvocation() {
        super(TraceOperation.class);
    }
}
