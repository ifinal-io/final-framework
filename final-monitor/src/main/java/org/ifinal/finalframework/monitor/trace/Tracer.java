package org.ifinal.finalframework.monitor.trace;

import org.ifinal.finalframework.aop.Executor;
import org.ifinal.finalframework.monitor.context.TraceContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Tracer extends Executor {

    void start(TraceContext context);

    void stop(TraceContext context);

}
