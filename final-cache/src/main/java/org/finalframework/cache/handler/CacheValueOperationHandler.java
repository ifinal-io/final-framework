

package org.finalframework.cache.handler;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.cache.Cache;
import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.operation.CacheValueOperation;
import org.finalframework.json.Json;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see Cacheable
 * @see Cache#get(Object, Object, Type, Class)
 * @since 1.0
 */
@SuppressWarnings("all")
@SpringComponent
public class CacheValueOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CacheValueOperation> {

    @Override
    public Void before(Cache cache, OperationContext<CacheValueOperation> context) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);
        final CacheValueOperation operation = context.operation();

        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + context.operation());
        }
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
        final Type type = operation.parameterType();
        logger.info("==> cache get: key={},field={}", key, field);
        Object cacheValue = cache.get(key, field, type, null);
        logger.info("<== value: {}", Json.toJson(cacheValue));
        context.args()[operation.index()] = cacheValue;
        return null;
    }

}
