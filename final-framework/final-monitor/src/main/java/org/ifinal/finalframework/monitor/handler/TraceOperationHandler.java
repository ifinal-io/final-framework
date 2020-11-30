package org.ifinal.finalframework.monitor.handler;

import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.monitor.annotation.MonitorTrace;
import org.ifinal.finalframework.monitor.context.TraceContext;
import org.ifinal.finalframework.monitor.executor.Tracer;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class TraceOperationHandler implements OperationHandler<Tracer, MonitorTrace> {

    private static final String TRACE_CONTEXT = "traceContext";

    @Override
    public Object before(Tracer executor, OperationContext context) {

        TraceContext traceContext = new TraceContext();
        traceContext.setTrace(context.annotationAttributes().getString("trace"));
        context.addAttribute(TRACE_CONTEXT, traceContext);
        executor.start(traceContext);
        return null;
    }


    @Override
    public void after(Tracer executor, OperationContext context, Object result, Throwable throwable) {
        executor.stop(context.getAttribute(TRACE_CONTEXT));
    }
}
