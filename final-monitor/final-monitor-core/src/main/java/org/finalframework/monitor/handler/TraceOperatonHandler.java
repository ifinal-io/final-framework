package org.finalframework.monitor.handler;

import org.finalframework.monitor.executor.Tracer;
import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;

/**
 * @author ilikly
 * @version 1.0
 * @date 2019-07-09 17:42
 * @since 1.0
 */
public class TraceOperatonHandler implements OperationHandler<Tracer, TraceOperation> {
    @Override
    public Object before(Tracer executor, OperationContext<TraceOperation> context, Object result) {
        executor.start(context);
        return null;
    }

    @Override
    public void afterReturning(Tracer executor, OperationContext<TraceOperation> context, Object result) {

    }

    @Override
    public void afterThrowing(Tracer executor, OperationContext<TraceOperation> context, Throwable throwable) {

    }

    @Override
    public void after(Tracer executor, OperationContext<TraceOperation> context, Object result, Throwable throwable) {
        executor.stop(context);
    }
}
