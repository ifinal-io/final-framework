package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheOperationInvocation;
import com.ilikly.finalframework.cache.CacheOperationInvocationContext;
import com.ilikly.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 * @see com.ilikly.finalframework.cache.annotation.Cacheable
 * @see CacheableOperation
 */
public class CacheGetOperationInvocation implements CacheOperationInvocation<Object> {

    @Override
    @SuppressWarnings("all")
    public Object invoke(Cache cache, CacheOperationInvocationContext context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = context.createEvaluationContext(result);
        final Object key = context.generateKey(evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null key, operation=" + context.operation());
        }
        final Object field = context.generateField(evaluationContext);
        final Type genericReturnType = context.method().getGenericReturnType();
        Object cacheValue;
        if (field == null) {
            logger.info("==> cache get: key={}", key);
            cacheValue = cache.get(key, genericReturnType, context.view());
        } else {
            logger.info("==> cache hget: key={},field={}", key, field);
            cacheValue = cache.hget(key, field, genericReturnType, context.view());
        }
        logger.info("<== result: {}", Json.toJson(cacheValue));
        return cacheValue;

    }
}
