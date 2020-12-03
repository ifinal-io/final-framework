package org.ifinal.finalframework.monitor.handler;

import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.monitor.context.TraceContext;
import org.ifinal.finalframework.monitor.trace.Tracer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class TraceInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport implements InterceptorHandler<Tracer, AnnotationAttributes> {

    private static final String TRACE_CONTEXT = "traceContext";

    @Override
    public Object before(Tracer executor, InvocationContext context, AnnotationAttributes annotation) {
        TraceContext traceContext = new TraceContext();
        traceContext.setTrace(annotation.getString("trace"));
        context.addAttribute(TRACE_CONTEXT, traceContext);
        executor.start(traceContext);
        return null;
    }

    @Override
    public void after(Tracer executor, InvocationContext context, AnnotationAttributes annotation, Object result, Throwable throwable) {
        executor.stop(context.getAttribute(TRACE_CONTEXT));
    }
}
