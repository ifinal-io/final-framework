package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.annotation.Cacheable;
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
public class CacheableInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CacheableOperation, CacheableProperty> {

    @Override
    public Object before(Cache cache, CacheOperationContext<CacheableOperation, CacheableProperty> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
        final CacheableOperation operation = context.metadata().getOperation();
        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null key, operation=" + context.operation());
        }
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
        context.property(new CacheablePropertyImpl(key, field));

        final Type genericReturnType = context.genericReturnType();
        Object cacheValue;
        logger.info("==> cache get: key={},field={}", key, field);
        cacheValue = cache.get(key, field, genericReturnType, context.view());
        logger.info("<== value: {}", Json.toJson(cacheValue));
        return cacheValue;
    }

    @Override
    public void afterReturning(Cache cache, CacheOperationContext<CacheableOperation, CacheableProperty> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
        if (!isConditionPassing(context.operation().condition(), context.metadata(), evaluationContext)) {
            return;
        }
        final Object key = context.property().key();
        final Object field = context.property().field();
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

    }


    private static class CacheablePropertyImpl implements CacheableProperty, Serializable {
        private final Object key;
        private final Object field;

        public CacheablePropertyImpl(Object key, Object field) {
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
