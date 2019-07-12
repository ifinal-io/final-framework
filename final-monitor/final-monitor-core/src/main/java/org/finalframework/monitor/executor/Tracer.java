package org.finalframework.monitor.executor;

import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.OperationContext;

/**
 * @author ilikly
 * @version 1.0
 * @date 2019-07-09 16:54
 * @since 1.0
 */
public interface Tracer extends Executor {

    void start(OperationContext<TraceOperation> context);

    void stop(OperationContext<TraceOperation> context);

}
