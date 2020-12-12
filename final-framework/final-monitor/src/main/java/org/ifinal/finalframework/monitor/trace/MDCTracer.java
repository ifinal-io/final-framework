package org.ifinal.finalframework.monitor.trace;


import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.core.generator.TraceGenerator;
import org.ifinal.finalframework.core.generator.UUIDTraceGenerator;
import org.ifinal.finalframework.monitor.context.TraceContext;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.MDC;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Primary
@Component
public class MDCTracer implements Tracer {
    private static final String MDC_TRACER = "mdcTracer";

    private static final TraceGenerator traceGenerator = UUIDTraceGenerator.INSTANCE;

    @Override
    public void start(final TraceContext context) {

        String trace = context.getTrace();
        String value = MDC.get(trace);
        if (Asserts.isBlank(value)) {
            value = traceGenerator.generate(null);
            MDC.put(trace, value);
            MDC.put(MDC_TRACER, Boolean.TRUE.toString());
            logger.info("put trace: trace={},value={}", trace, value);
        }
    }

    @Override
    public void stop(final TraceContext context) {

        if (Boolean.TRUE.equals(Boolean.valueOf(MDC.get(MDC_TRACER)))) {
            String trace = context.getTrace();
            String value = MDC.get(trace);
            logger.info("remove trace: trace={},value={}", trace, value);
            MDC.remove(trace);
        }
    }
}
