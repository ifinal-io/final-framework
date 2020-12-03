package org.ifinal.finalframework.monitor.trace;

import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.context.exception.InternalServerException;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Parameter;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
    public Object before(Tracer executor, InvocationContext context, Boolean annotation) {
        context.addAttribute(TRACE_START, System.currentTimeMillis());

        MethodMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass() + "." + metadata.getMethod().getName());


        AtomicInteger atomicInteger = methodDeepCounter.get();
        if (Objects.isNull(atomicInteger)) {
            atomicInteger = new AtomicInteger(0);
            methodDeepCounter.set(atomicInteger);
        }

        StringBuilder sb = new StringBuilder();

        int count = atomicInteger.incrementAndGet();
        for (int i = 0; i < count; i++) {
            sb.append(TAB);
        }

        String tab = sb.toString();

        logger.info("====START====================================================START====");
        logger.info("Class:{}", metadata.getTargetClass().getName());
        logger.info("{},Method:{}", tab, metadata.getMethod().getName());

        Object[] args = context.args();
        Parameter[] parameters = metadata.getMethod().getParameters();

        for (int i = 0; i < args.length; i++) {
            logger.info("{},Parameter: {}={}", tab, parameters[i].getName(), args[i].toString());
        }


        return null;
    }

    @Override
    public void afterReturning(Tracer executor, InvocationContext context, Boolean annotation, Object result) {

    }

    @Override
    public void afterThrowing(Tracer executor, InvocationContext context, Boolean annotation, Throwable throwable) {

    }

    @Override
    public void after(Tracer executor, InvocationContext context, Boolean annotation, Object result, Throwable throwable) {
        MethodMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass() + "." + metadata.getMethod().getName());

        AtomicInteger atomicInteger = methodDeepCounter.get();
        if (Objects.isNull(atomicInteger)) {
            atomicInteger = new AtomicInteger(0);
            methodDeepCounter.set(atomicInteger);
        }

        StringBuilder sb = new StringBuilder();

        int count = atomicInteger.get();
        for (int i = 0; i < count; i++) {
            sb.append(TAB);
        }

        String tab = sb.toString();

        long start = context.getAttribute(TRACE_START);
        final long duration = System.currentTimeMillis() - start;
        logger.info("{},Duration:{}", tab, Duration.ofMinutes(duration).toString());

        if (Objects.nonNull(throwable)) {
            if (throwable instanceof IException && !(throwable instanceof InternalServerException)) {
                IException exception = (IException) throwable;
                logger.warn("exception: code={},message={}", exception.getCode(), exception.getMessage());
            } else {
                logger.error("exception: {}", throwable.getMessage(), throwable);
            }
        } else {
            logger.info("{},Result: {}", tab, Json.toJson(result));
        }

        int i = atomicInteger.decrementAndGet();
        if (i == 0) {
            methodDeepCounter.remove();
        }


        logger.info("====END====================================================END====");


    }
}
