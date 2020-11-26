package org.ifinal.finalframework.monitor.executor;


import org.ifinal.finalframework.core.generator.TraceGenerator;
import org.ifinal.finalframework.core.generator.UUIDTraceGenerator;
import org.ifinal.finalframework.monitor.context.TraceContext;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Primary
@Component
public class MDCTracer implements Tracer {
    private static final Logger logger = LoggerFactory.getLogger(MDCTracer.class);
    private static final String mdcTracer = "mdcTracer";

    private TraceGenerator traceGenerator = UUIDTraceGenerator.instance;

    @Override
    public void start(TraceContext context) {
        String trace = context.getTrace();
        String value = MDC.get(trace);
        if (Asserts.isBlank(value)) {
            value = traceGenerator.generate(null);
            MDC.put(trace, value);
            MDC.put(mdcTracer, Boolean.TRUE.toString());
            logger.info("put trace: trace={},value={}", trace, value);
        }
    }

    @Override
    public void stop(TraceContext context) {
        if (Boolean.TRUE.equals(Boolean.valueOf(MDC.get(mdcTracer)))) {
            String trace = context.getTrace();
            String value = MDC.get(trace);
            logger.info("remove trace: trace={},value={}", trace, value);
            MDC.remove(trace);
        }
    }
}
