package org.finalframework.cache.handler;

import org.finalframework.cache.Cache;
import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.operation.CacheableOperation;
import org.finalframework.json.Json;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

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
public class CacheableOperationHandler extends AbsCacheInvocationSupport implements OperationHandler<Cache, CacheableOperation> {
    private static final String KEY = "key";
    private static final String FIELD = "field";

    @Override
    public Object before(Cache cache, OperationContext<CacheableOperation> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
        final CacheableOperation operation = context.operation();
        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + operation);
        }
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
        context.addAttribute(KEY, key);
        context.addAttribute(FIELD, field);
        final Type genericReturnType = context.metadata().getGenericReturnType();
        Object cacheValue;
        logger.info("==> cache get: key={},field={}", key, field);
        cacheValue = cache.get(key, field, genericReturnType, context.view());
        logger.info("<== value: {}", Json.toJson(cacheValue));
        return cacheValue;
    }

    @Override
    public void afterReturning(Cache cache, OperationContext<CacheableOperation> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
        if (!isConditionPassing(context.operation().condition(), context.metadata(), evaluationContext)) {
            return;
        }
        final Object key = context.getAttribute(KEY);
        final Object field = context.getAttribute(FIELD);
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


}
