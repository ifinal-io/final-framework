package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.interceptor.DefaultCacheOperationExpressionEvaluator;
import org.finalframework.cache.operation.CacheDelOperation;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationMetadata;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 */
public class CacheDelInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CacheDelOperation> {

    @Override
    public Void before(Cache cache, OperationContext<CacheDelOperation> context, Object result) {
        if (CutPoint.BEFORE == context.operation().point()) return null;
        invocation(cache, context, result, null);
        return null;
    }

    @Override
    public void afterReturning(Cache cache, OperationContext<CacheDelOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING == context.operation().point()) return;
        invocation(cache, context, result, null);
    }

    @Override
    public void afterThrowing(Cache cache, OperationContext<CacheDelOperation> context, Throwable throwable) {
        if (CutPoint.AFTER_THROWING == context.operation().point()) return;
        invocation(cache, context, DefaultCacheOperationExpressionEvaluator.NO_RESULT, throwable);
    }

    @Override
    public void after(Cache cache, OperationContext<CacheDelOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER == context.operation().point()) return;
        invocation(cache, context, result, throwable);
    }

    private void invocation(Cache cache, OperationContext<CacheDelOperation> context, Object result, Throwable throwable) {
        final OperationMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final CacheDelOperation operation = context.operation();
        if (isConditionPassing(operation.condition(), metadata, evaluationContext)) {
            final Object key = generateKey(operation.key(), operation.delimiter(), metadata, evaluationContext);
            if (key == null) {
                throw new IllegalArgumentException("the cache action generate null key, action=" + operation);
            }
            final Object field = generateField(operation.field(), operation.delimiter(), metadata, evaluationContext);
            logger.info("==> cache del: key={},field={}", key, field);
            Boolean flag = cache.del(key, field);
            logger.info("<== value: {}", flag);
        }
    }

}
