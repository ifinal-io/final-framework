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
    public Object before(final Tracer executor, final InvocationContext context, final AnnotationAttributes annotation) {

        TraceContext traceContext = new TraceContext();
        traceContext.setTrace(annotation.getString("trace"));
        context.addAttribute(TRACE_CONTEXT, traceContext);
        executor.start(traceContext);
        return null;
    }

    @Override
    public void after(final Tracer executor, final InvocationContext context, final AnnotationAttributes annotation, final Object result, final Throwable throwable) {

        executor.stop(context.getAttribute(TRACE_CONTEXT));
    }
}
