package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheOperationInvocation;
import com.ilikly.finalframework.cache.CacheOperationInvocationContext;
import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see com.ilikly.finalframework.cache.annotation.Cacheable
 * @see CacheableOperation
 * @since 1.0
 */
@SuppressWarnings("all")
public class CacheableOperationInvocation implements CacheOperationInvocation<Void> {

    @Override
    public Void invoke(Cache cache, CacheOperationInvocationContext context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = context.createEvaluationContext(result);
        if (!context.isConditionPassing(evaluationContext)) {
            return null;
        }

        final Object key = context.generateKey(evaluationContext);
        final Object field = context.generateField(evaluationContext);

        Object cacheValue = result;

        if (Assert.nonEmpty(context.operation().value())) {
            cacheValue = context.generateValue(evaluationContext);
        }

        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = context.generateExpire(evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = context.operation().ttl();
            timeUnit = context.operation().timeUnit();
        }

        logger.info("==> cache set: key={},field={},ttl={},timeUnit={}", key, field, ttl, timeUnit);
        logger.info("==> cache value: {}", Json.toJson(cacheValue));
        cache.set(key, field, cacheValue, ttl, timeUnit, context.view());

        return null;

    }
}
