package org.ifinal.finalframework.cache.handler;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @see Cacheable
 * @since 1.0.0
 */
@Component
public class CacheableInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport implements CacheInterceptorHandler {

    private static final String KEY = "key";

    private static final String FIELD = "field";

    @Override
    public Object before(final @NonNull Cache cache, final @NonNull InvocationContext context, final @NonNull AnnotationAttributes operation) {

        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);
        final Object key = generateKey(operation.getStringArray(KEY), operation.getString("delimiter"), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + operation);
        }
        final Object field = generateField(operation.getStringArray(FIELD), operation.getString("delimiter"), context.metadata(), evaluationContext);
        context.addAttribute(KEY, key);
        context.addAttribute(FIELD, field);
        final Type genericReturnType = context.metadata().getGenericReturnType();
        Object cacheValue;
        if (logger.isInfoEnabled()) {
            logger.info("==> cache get: key={},field={}", key, field);
        }
        cacheValue = cache.get(key, field, genericReturnType, context.view());
        if (logger.isInfoEnabled()) {
            logger.info("<== value: {}", Json.toJson(cacheValue));
        }
        return cacheValue;
    }

    @Override
    public void afterReturning(final @NonNull Cache cache, final @NonNull InvocationContext context, final @NonNull AnnotationAttributes annotation,
        final Object result) {

        if (Objects.isNull(result)) {
            return;
        }

        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
        if (!isConditionPassing(annotation.getString("condition"), context.metadata(), evaluationContext)) {
            return;
        }
        final Object key = context.getAttribute(KEY);
        final Object field = context.getAttribute(FIELD);
        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = generateExpire(annotation.getString("expire"), context.metadata(), evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = annotation.getNumber("ttl");
            timeUnit = annotation.getEnum("timeunit");
        }

        if (logger.isInfoEnabled()) {
            logger.info("==> cache set: key={},field={},ttl={},timeunit={}", key, field, ttl, timeUnit);
            logger.info("==> cache value: {}", Json.toJson(result));
        }
        cache.set(key, field, result, ttl, timeUnit, context.view());

    }

}
