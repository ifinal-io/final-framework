package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.OperationMetadata;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.operation.CacheDelOperation;
import org.ifinal.finalframework.context.expression.MethodExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheDelOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CacheDelOperation> {

    @Override
    public Void before(@NonNull Cache cache, @NonNull OperationContext<CacheDelOperation> context) {
        if (CutPoint.BEFORE == context.operation().point()) {
            invocation(cache, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(@NonNull Cache cache, @NonNull OperationContext<CacheDelOperation> context, Object result) {
        if (CutPoint.AFTER_RETURNING != context.operation().point()) {
            invocation(cache, context, result, null);
        }
    }

    @Override
    public void afterThrowing(@NonNull Cache cache, @NonNull OperationContext<CacheDelOperation> context, @NonNull Throwable throwable) {
        if (CutPoint.AFTER_THROWING != context.operation().point()) {
            invocation(cache, context, MethodExpressionEvaluator.NO_RESULT, throwable);
        }
    }

    @Override
    public void after(@NonNull Cache cache, @NonNull OperationContext<CacheDelOperation> context, Object result, Throwable throwable) {
        if (CutPoint.AFTER != context.operation().point()) {
            invocation(cache, context, result, throwable);
        }
    }

    private void invocation(@NonNull Cache cache, @NonNull OperationContext<CacheDelOperation> context, Object result, Throwable throwable) {
        final OperationMetadata<?> metadata = context.metadata();
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
