package org.ifinal.finalframework.monitor.handler;

import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.monitor.context.TraceContext;
import org.ifinal.finalframework.monitor.executor.Tracer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class TraceInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport<Tracer> {

    private static final String TRACE_CONTEXT = "traceContext";

    @Override
    protected Object doBefore(Tracer executor, InvocationContext context, AnnotationAttributes annotation) {
        TraceContext traceContext = new TraceContext();
        traceContext.setTrace(annotation.getString("trace"));
        context.addAttribute(TRACE_CONTEXT, traceContext);
        executor.start(traceContext);
        return null;
    }

    @Override
    protected void doAfter(Tracer executor, InvocationContext context, AnnotationAttributes annotation, Object result, Throwable throwable) {
        executor.stop(context.getAttribute(TRACE_CONTEXT));
    }
}
