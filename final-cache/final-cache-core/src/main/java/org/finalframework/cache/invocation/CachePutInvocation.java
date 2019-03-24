package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.operation.CachePutOperation;
import org.finalframework.core.Assert;
import org.finalframework.json.Json;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see Cacheable
 * @since 1.0
 */
public class CachePutInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CachePutOperation, Void> {

    @Override
    public Void before(Cache cache, CacheOperationContext<CachePutOperation, Void> context, Object result) {
        if (CutPoint.BEFORE == context.operation().point()) {
            invocation(cache, context, result, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Cache cache, CacheOperationContext<CachePutOperation, Void> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) {
            invocation(cache, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Cache cache, CacheOperationContext<CachePutOperation, Void> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) {
            invocation(cache, context, null, throwable);
        }
    }

    private void invocation(Cache cache, CacheOperationContext<CachePutOperation, Void> context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final CachePutOperation operation = context.operation();
        if (!isConditionPassing(operation.condition(), context.metadata(), evaluationContext)) {
            return;
        }

        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
        Object cacheValue = result;

        if (Assert.nonBlank(operation.value())) {
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
