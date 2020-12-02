package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0.0
 * @see Cacheable
 * @see Cache#get(Object, Object, Type, Class)
 * @since 1.0.0
 */
@SuppressWarnings("all")
@Component
public class CacheValueInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport {

    @Override
    protected Object doBefore(Cache cache, InvocationContext context, AnnotationAttributes operation) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);

        String delimiter = operation.getString("delimiter");
        final Object key = generateKey(operation.getStringArray("key"), delimiter, context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + context);
        }
        final Object field = generateField(operation.getStringArray("field"), delimiter, context.metadata(), evaluationContext);
        final Type type = (Type) operation.get("parameterType");
        logger.info("==> cache get: key={},field={}", key, field);
        Object cacheValue = cache.get(key, field, type, null);
        logger.info("<== value: {}", Json.toJson(cacheValue));
        context.args()[(int) operation.getNumber("parameterIndex")] = cacheValue;

        return null;
    }

}
