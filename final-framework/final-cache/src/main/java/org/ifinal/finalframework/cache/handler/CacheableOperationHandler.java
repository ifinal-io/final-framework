package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.AnnotationInvocationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0.0
 * @see Cacheable
 * @since 1.0.0
 */
@SuppressWarnings("all")
@Component
public class CacheableOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, Cacheable> {
    private static final String KEY = "key";
    private static final String FIELD = "field";


    @Override
    public Object before(Cache cache, AnnotationInvocationContext context) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);
        final AnnotationAttributes operation = context.annotationAttributes();
        final Object key = generateKey(operation.getStringArray(KEY), operation.getString("delimiter"), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + operation);
        }
        final Object field = generateField(operation.getStringArray(FIELD), operation.getString("delimiter"), context.metadata(), evaluationContext);
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
    public void afterReturning(Cache cache, AnnotationInvocationContext context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
        AnnotationAttributes annotationAttributes = context.annotationAttributes();
        if (!isConditionPassing(annotationAttributes.getString("condition"), context.metadata(), evaluationContext)) {
            return;
        }
        final Object key = context.getAttribute(KEY);
        final Object field = context.getAttribute(FIELD);
        final Object cacheValue = result;
        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = generateExpire(annotationAttributes.getString("expire"), context.metadata(), evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = annotationAttributes.getNumber("ttl");
            timeUnit = annotationAttributes.getEnum("timeunit");
        }

        logger.info("==> cache set: key={},field={},ttl={},timeunit={}", key, field, ttl, timeUnit);
        logger.info("==> cache value: {}", Json.toJson(cacheValue));
        cache.set(key, field, cacheValue, ttl, timeUnit, context.view());

    }


}
