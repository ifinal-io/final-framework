package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.CachePut;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.json.Json;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0.0
 * @see Cacheable
 * @since 1.0.0
 */
@Component
public class CachePutOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CachePut> {

    @Override
    public Void before(Cache cache, OperationContext context) {
        if (CutPoint.BEFORE == context.cutPoint()) {
            invocation(cache, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Cache cache, OperationContext context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.cutPoint()) {
            invocation(cache, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Cache cache, OperationContext context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.cutPoint()) {
            invocation(cache, context, null, throwable);
        }
    }

    @Override
    public void after(Cache cache, OperationContext context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.cutPoint()) {
            invocation(cache, context, result, throwable);
        }
    }

    private void invocation(Cache cache, OperationContext context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final AnnotationAttributes operation = context.annotationAttributes();
        if (!isConditionPassing(operation.getString("condition"), context.metadata(), evaluationContext)) {
            return;
        }

        final Object key = generateKey(operation.getStringArray("key"), operation.getString("delimiter"), context.metadata(), evaluationContext);
        final Object field = generateField(operation.getStringArray("field"), operation.getString("delimiter"), context.metadata(), evaluationContext);
        Object cacheValue = result;

        String value = operation.getString("value");
        if (Asserts.nonBlank(value)) {
            cacheValue = generateValue(value, context.metadata(), evaluationContext);
        }

        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = generateExpire(operation.getString("expire"), context.metadata(), evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = operation.getNumber("ttl");
            timeUnit = operation.getEnum("timeUnit");
        }

        logger.info("==> cache set: key={},field={},ttl={},timeunit={}", key, field, ttl, timeUnit);
        logger.info("==> cache value: {}", Json.toJson(cacheValue));
        cache.set(key, field, cacheValue, ttl, timeUnit, context.view());
    }
}
