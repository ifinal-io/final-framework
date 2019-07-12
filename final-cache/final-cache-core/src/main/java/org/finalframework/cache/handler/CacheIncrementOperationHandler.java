package org.finalframework.cache.handler;


import org.finalframework.cache.Cache;
import org.finalframework.cache.operation.CacheIncrementOperation;
import org.finalframework.core.PrimaryTypes;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 22:33:02
 * @since 1.0
 */
public class CacheIncrementOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CacheIncrementOperation> {

    @Override
    public Void before(Cache cache, OperationContext<CacheIncrementOperation> context, Object result) {
        if (CutPoint.BEFORE == context.operation().point()) {
            doCacheIncrement(cache, context, result, null);
        }
        return null;
    }

    @Override
    public void afterReturning(Cache cache, OperationContext<CacheIncrementOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) {
            doCacheIncrement(cache, context, result, null);
        }
    }

    @Override
    public void afterThrowing(Cache cache, OperationContext<CacheIncrementOperation> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) {
            doCacheIncrement(cache, context, null, throwable);
        }
    }

    @Override
    public void after(Cache cache, OperationContext<CacheIncrementOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.operation().point()) {
            doCacheIncrement(cache, context, result, throwable);
        }
    }

    private void doCacheIncrement(Cache cache, OperationContext<CacheIncrementOperation> context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final CacheIncrementOperation operation = context.operation();
        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + context.operation());
        }
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);

        final boolean hasKey = cache.isExists(key, field);

        final Class<? extends Number> type = operation.type();
        boolean incremented = false;
        if (PrimaryTypes.isLong(type) || PrimaryTypes.isInteger(type)) {
            final Long value = generateValue(operation.value(), context.metadata(), evaluationContext, Long.class);
            if (value == null) {
                throw new NullPointerException("increment value is null");
            }
            logger.info("==> cache increment: key={}, field={}, value={}", key, field, value);
            final Long increment = cache.increment(key, field, value);
            incremented = increment != null;
            logger.info("<== result: {}", increment);
        } else if (PrimaryTypes.isFloat(type) || PrimaryTypes.isDouble(type)) {
            final Double value = generateValue(operation.value(), context.metadata(), evaluationContext, Double.class);
            if (value == null) {
                throw new NullPointerException("increment value is null");
            }
            logger.info("==> cache increment: key={}, field={}, value={}", key, field, value);
            final Double increment = cache.increment(key, field, value);
            incremented = increment != null;
            logger.info("<== result: {}", increment);
        } else {
            throw new IllegalArgumentException(String.format("CacheIncrementOperation value type %s is not support.", type.getCanonicalName()));
        }

        if (!hasKey && incremented) {
            Long ttl;
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            Object expired = generateExpire(context.operation().expire(), context.metadata(), evaluationContext);

            if (expired != null) {
                if (expired instanceof Date) {
                    ttl = ((Date) expired).getTime() - System.currentTimeMillis();
                } else {
                    throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
                }
            } else {
                ttl = context.operation().ttl();
                timeUnit = context.operation().timeunit();
            }

            if (ttl != null && ttl > 0) {
                cache.expire(key, ttl, timeUnit);
            }

        }

    }
}
