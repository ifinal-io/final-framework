package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.CacheValue;
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
public class CacheValueOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CacheValue> {

    @Override
    public Void before(Cache cache, OperationContext context) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);
        final AnnotationAttributes operation = context.annotationAttributes();

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
