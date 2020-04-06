package org.finalframework.monitor.handler;

import org.finalframework.monitor.context.TraceContext;
import org.finalframework.monitor.executor.Tracer;
import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 17:42
 * @since 1.0
 */
@SpringComponent
public class TraceOperationHandler implements OperationHandler<Tracer, TraceOperation> {

    private static final String TRACE_CONTEXT = "traceContext";

    @Override
    public Object before(Tracer executor, OperationContext<TraceOperation> context) {

        TraceContext traceContext = new TraceContext();
        traceContext.setTrace(context.operation().trace());
        context.addAttribute(TRACE_CONTEXT, traceContext);
        executor.start(traceContext);
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
        executor.stop(context.getAttribute(TRACE_CONTEXT));
    }
}
