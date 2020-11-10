package org.finalframework.monitor.executor;

import org.finalframework.aop.Executor;
import org.finalframework.monitor.context.TraceContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 16:54
 * @since 1.0
 */
public interface Tracer extends Executor {

    void start(TraceContext context);

    void stop(TraceContext context);

}
