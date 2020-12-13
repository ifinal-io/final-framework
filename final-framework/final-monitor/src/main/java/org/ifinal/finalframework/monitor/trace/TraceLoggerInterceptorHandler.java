package org.ifinal.finalframework.monitor.trace;

import java.lang.reflect.Parameter;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.ifinal.finalframework.annotation.core.IException;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.context.exception.InternalServerException;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class TraceLoggerInterceptorHandler implements InterceptorHandler<Tracer, Boolean> {

    private static final String TRACE_START = "traceStart";

    private static final String TAB = "    ";

    ThreadLocal<AtomicInteger> methodDeepCounter = new ThreadLocal<>();

    @Override
    public Object before(final @NonNull Tracer executor, final @NonNull InvocationContext context,
        final @NonNull Boolean annotation) {

        context.addAttribute(TRACE_START, System.currentTimeMillis());

        MethodMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass() + "." + metadata.getMethod().getName());

        String tab = tab();

        logger.info("====START====================================================START====");
        logger.info("Class:{}", metadata.getTargetClass().getName());
        logger.info("{},Method:{}", tab, metadata.getMethod().getName());

        Object[] args = context.args();
        Parameter[] parameters = metadata.getMethod().getParameters();

        for (int i = 0; i < args.length; i++) {
            logger.info("{},Parameter: {}={}", tab, parameters[i].getName(), args[i]);
        }

        return null;
    }

    private AtomicInteger counter() {
        AtomicInteger atomicInteger = methodDeepCounter.get();
        if (Objects.isNull(atomicInteger)) {
            atomicInteger = new AtomicInteger(0);
            methodDeepCounter.set(atomicInteger);
        }
        return atomicInteger;
    }

    private String tab() {

        final AtomicInteger atomicInteger = counter();

        StringBuilder sb = new StringBuilder();

        int count = atomicInteger.get();
        for (int i = 0; i < count; i++) {
            sb.append(TAB);
        }

        return sb.toString();
    }

    @Override
    public void after(final @NonNull Tracer executor, final @NonNull InvocationContext context,
        final @NonNull Boolean annotation, final Object result,
        final Throwable throwable) {

        MethodMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass() + "." + metadata.getMethod().getName());

        final AtomicInteger atomicInteger = counter();
        final String tab = tab();

        long start = context.getAttribute(TRACE_START);
        final long duration = System.currentTimeMillis() - start;
        if (logger.isInfoEnabled()) {
            logger.info("{},Duration:{}", tab, Duration.ofMinutes(duration));
        }

        if (Objects.nonNull(throwable)) {
            if (throwable instanceof IException && !(throwable instanceof InternalServerException)) {
                IException exception = (IException) throwable;
                logger.warn("exception: code={},message={}", exception.getCode(), exception.getMessage());
            } else {
                logger.error("exception: {}", throwable.getMessage(), throwable);
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("{},Result: {}", tab, Json.toJson(result));
            }
        }

        int i = atomicInteger.decrementAndGet();
        if (i == 0) {
            methodDeepCounter.remove();
        }

        logger.info("====END====================================================END====");

    }

}
