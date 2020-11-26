package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.cache.operation.CachePutOperation;
import org.ifinal.finalframework.json.Json;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CachePutOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CachePutOperation> {

    @Override
    public Void before(Cache cache, OperationContext<CachePutOperation> context) {
        if (CutPoint.BEFORE == context.operation().point()) {
            invocation(cache, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Cache cache, OperationContext<CachePutOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) {
            invocation(cache, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Cache cache, OperationContext<CachePutOperation> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) {
            invocation(cache, context, null, throwable);
        }
    }

    @Override
    public void after(Cache cache, OperationContext<CachePutOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.operation().point()) {
            invocation(cache, context, result, throwable);
        }
    }

    private void invocation(Cache cache, OperationContext<CachePutOperation> context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final CachePutOperation operation = context.operation();
        if (!isConditionPassing(operation.condition(), context.metadata(), evaluationContext)) {
            return;
        }

        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
        Object cacheValue = result;

        if (Asserts.nonBlank(operation.value())) {
            cacheValue = generateValue(operation.value(), context.metadata(), evaluationContext);
        }

        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = generateExpire(operation.expire(), context.metadata(), evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = operation.ttl();
            timeUnit = operation.timeUnit();
        }

        logger.info("==> cache set: key={},field={},ttl={},timeunit={}", key, field, ttl, timeUnit);
        logger.info("==> cache value: {}", Json.toJson(cacheValue));
        cache.set(key, field, cacheValue, ttl, timeUnit, context.view());
    }
}
