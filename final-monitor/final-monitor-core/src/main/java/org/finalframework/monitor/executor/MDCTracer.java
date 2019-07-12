package org.finalframework.monitor.executor;

import org.finalframework.core.Assert;
import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.annotation.OperationExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author ilikly
 * @version 1.0
 * @date 2019-07-09 16:58
 * @since 1.0
 */
@OperationExecutor(Tracer.class)
public class MDCTracer implements Tracer {

    private static final Logger logger = LoggerFactory.getLogger(MDCTracer.class);

    private static final String mdcTracer = "mdcTracer";

    @Override
    public void start(OperationContext<TraceOperation> context) {
        String trace = context.operation().trace();
        String value = MDC.get(trace);
        if (Assert.isBlank(value)) {
            value = UUID.randomUUID().toString();
            MDC.put(trace, value);
            logger.info("put trace: trace={},value={}", trace, value);
            context.addAttribute(mdcTracer, true);
        }
    }

    @Override
    public void stop(OperationContext<TraceOperation> context) {
        if (Boolean.TRUE.equals(context.getAttribute(mdcTracer))) {
            String trace = context.operation().trace();
            String value = MDC.get(trace);
            logger.info("remove trace: trace={},value={}", trace, value);
            MDC.remove(trace);
        }
    }
}
