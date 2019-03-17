package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.annotation.enums.InvocationTime;
import org.finalframework.cache.interceptor.DefaultCacheOperationExpressionEvaluator;
import org.finalframework.cache.operation.CacheDelOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 */
public class CacheDelInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CacheDelOperation, Void, Void, Void> {

    @Override
    public Void before(Cache cache, CacheOperationContext<CacheDelOperation, Void> context, Object result) {
        if (InvocationTime.BEFORE == context.operation().invocationTime()) return null;
        invocation(cache, context, result, null);
        return null;
    }

    @Override
    public Void afterReturning(Cache cache, CacheOperationContext<CacheDelOperation, Void> context, Object result) {
        if (InvocationTime.AFTER_RETURNING == context.operation().invocationTime()) return null;
        invocation(cache, context, result, null);
        return null;
    }

    @Override
    public Void afterThrowing(Cache cache, CacheOperationContext<CacheDelOperation, Void> context, Throwable throwable) {
        if (InvocationTime.AFTER_THROWING == context.operation().invocationTime()) return null;
        invocation(cache, context, DefaultCacheOperationExpressionEvaluator.NO_RESULT, throwable);
        return null;
    }

    private void invocation(Cache cache, CacheOperationContext<CacheDelOperation, Void> context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final CacheDelOperation operation = context.operation();
        if (isConditionPassing(operation.condition(), context.metadata(), evaluationContext)) {
            final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
            if (key == null) {
                throw new IllegalArgumentException("the cache operation generate null key, operation=" + operation);
            }
            final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
            logger.info("==> cache del: key={},field={}", key, field);
            Boolean flag = cache.del(key, field);
            logger.info("<== value: {}", flag);
        }
    }

}
