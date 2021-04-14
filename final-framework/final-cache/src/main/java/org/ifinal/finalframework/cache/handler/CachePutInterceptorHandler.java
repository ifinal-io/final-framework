package org.ifinal.finalframework.cache.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.cache.annotation.Cache;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.json.Json;
import org.ifinal.finalframework.util.Asserts;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @see Cacheable
 * @since 1.0.0
 */
@Component
public class CachePutInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport implements
    CacheInterceptorHandler {

    @Override
    public void handle(final @NonNull Cache cache, final @NonNull InvocationContext context,
        final @NonNull AnnotationAttributes operation,
        final @Nullable Object result, final @Nullable Throwable throwable) {

        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        if (!isConditionPassing(operation.getString("condition"), context.metadata(), evaluationContext)) {
            return;
        }

        final Object key = generateKey(operation.getStringArray("key"), operation.getString("delimiter"),
            context.metadata(), evaluationContext);
        final Object field = generateField(operation.getStringArray("field"), operation.getString("delimiter"),
            context.metadata(), evaluationContext);
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

        if (logger.isInfoEnabled()) {
            logger.info("==> cache set: key={},field={},ttl={},timeunit={}", key, field, ttl, timeUnit);
            logger.info("==> cache value: {}", Json.toJson(cacheValue));
        }
        cache.set(key, field, cacheValue, ttl, timeUnit, context.view());
    }

}
