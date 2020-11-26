package org.ifinal.finalframework.monitor.handler;

import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.monitor.context.TraceContext;
import org.ifinal.finalframework.monitor.executor.Tracer;
import org.ifinal.finalframework.monitor.operation.TraceOperation;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
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
