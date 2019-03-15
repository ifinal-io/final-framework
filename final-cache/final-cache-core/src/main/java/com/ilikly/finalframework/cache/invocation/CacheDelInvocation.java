package com.ilikly.finalframework.cache.invocation;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheInvocation;
import com.ilikly.finalframework.cache.CacheOperationContext;
import com.ilikly.finalframework.cache.annotation.enums.CacheInvocationTime;
import com.ilikly.finalframework.cache.operation.CacheDelOperation;
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
    public boolean supports(CacheOperationContext<CacheDelOperation, Void> context, CacheInvocationTime invocationTime) {
        return context.operation().invocationTime() == invocationTime;
    }

    @Override
    public Void beforeInvocation(Cache cache, CacheOperationContext<CacheDelOperation, Void> context, Object result) {
        invocation(cache, context, result);
        return null;
    }

    @Override
    public Void afterInvocation(Cache cache, CacheOperationContext<CacheDelOperation, Void> context, Object result) {
        invocation(cache, context, result);
        return null;
    }


    private void invocation(Cache cache, CacheOperationContext<CacheDelOperation, Void> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result);
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
