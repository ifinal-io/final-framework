package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.annotation.enums.CacheInvocationTime;
import org.finalframework.cache.operation.CacheableOperation;
import org.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see Cacheable
 * @since 1.0
 */
@SuppressWarnings("all")
public class CacheableInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CacheableOperation, CacheableInvocationContext, Object, Void> {

    @Override
    public boolean supports(CacheOperationContext<CacheableOperation, CacheableInvocationContext> context, CacheInvocationTime invocationTime) {
        return true;
    }

    @Override
    public Object beforeInvocation(Cache cache, CacheOperationContext<CacheableOperation, CacheableInvocationContext> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result);
        final CacheableOperation operation = context.metadata().getOperation();
        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null key, operation=" + context.operation());
        }
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
        context.invocation(new CacheableInvocationContextImpl(key, field));

        final Type genericReturnType = context.genericReturnType();
        Object cacheValue;
        logger.info("==> cache get: key={},field={}", key, field);
        cacheValue = cache.get(key, field, genericReturnType, context.view());
        logger.info("<== value: {}", Json.toJson(cacheValue));
        return cacheValue;
    }

    @Override
    public Void afterInvocation(Cache cache, CacheOperationContext<CacheableOperation, CacheableInvocationContext> context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result);
        if (!isConditionPassing(context.operation().condition(), context.metadata(), evaluationContext)) {
            return null;
        }

        final Object key = context.invocation().key();
        final Object field = context.invocation().field();
        final Object cacheValue = result;

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

        logger.info("==> cache set: key={},field={},ttl={},timeunit={}", key, field, ttl, timeUnit);
        logger.info("==> cache value: {}", Json.toJson(cacheValue));
        cache.set(key, field, cacheValue, ttl, timeUnit, context.view());
        return null;
    }

    private static class CacheableInvocationContextImpl implements CacheableInvocationContext, Serializable {
        private final Object key;
        private final Object field;

        public CacheableInvocationContextImpl(Object key, Object field) {
            this.key = key;
            this.field = field;
        }

        @Override
        public Object key() {
            return key;
        }

        @Override
        public Object field() {
            return field;
        }
    }
}
