package com.ilikly.finalframework.cache.invocation;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheInvocation;
import com.ilikly.finalframework.cache.CacheOperationContext;
import com.ilikly.finalframework.cache.annotation.enums.CacheInvocationTime;
import com.ilikly.finalframework.cache.operation.CachePutOperation;
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
 * @since 1.0
 */
public class CachePutInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CachePutOperation, Void, Void, Void> {

    @Override
    public boolean supports(CacheOperationContext<CachePutOperation, Void> context, CacheInvocationTime invocationTime) {
        return CacheInvocationTime.AFTER == invocationTime;
    }

    @Override
    public Void beforeInvocation(Cache cache, CacheOperationContext<CachePutOperation, Void> context, Object result) {
        throw new UnsupportedOperationException("CachePut 不支持在方法执行之前调用");
    }

    @Override
    public Void afterInvocation(Cache cache, CacheOperationContext<CachePutOperation, Void> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result);
        final CachePutOperation operation = context.operation();
        if (!isConditionPassing(operation.condition(), context.metadata(), evaluationContext)) {
            return null;
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

        return null;
    }
}
