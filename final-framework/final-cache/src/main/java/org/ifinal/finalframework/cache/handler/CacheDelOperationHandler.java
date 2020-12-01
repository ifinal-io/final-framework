package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.AnnotationInvocationContext;
import org.ifinal.finalframework.aop.OperationHandler;
import org.ifinal.finalframework.aop.annotation.CutPoint;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.annotation.CacheDel;
import org.ifinal.finalframework.context.expression.MethodExpressionEvaluator;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheDelOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CacheDel> {

    @Override
    public Void before(@NonNull Cache cache, @NonNull AnnotationInvocationContext context) {
        if (CutPoint.BEFORE == context.cutPoint()) {
            invocation(cache, context, null, null);
        }
        return null;
    }

    @Override
    public void afterReturning(@NonNull Cache cache, @NonNull AnnotationInvocationContext context, Object result) {
        if (CutPoint.AFTER_RETURNING != context.cutPoint()) {
            invocation(cache, context, result, null);
        }
    }

    @Override
    public void afterThrowing(@NonNull Cache cache, @NonNull AnnotationInvocationContext context, @NonNull Throwable throwable) {
        if (CutPoint.AFTER_THROWING != context.cutPoint()) {
            invocation(cache, context, MethodExpressionEvaluator.NO_RESULT, throwable);
        }
    }

    @Override
    public void after(@NonNull Cache cache, @NonNull AnnotationInvocationContext context, Object result, Throwable throwable) {
        if (CutPoint.AFTER != context.cutPoint()) {
            invocation(cache, context, result, throwable);
        }
    }

    private void invocation(@NonNull Cache cache, @NonNull AnnotationInvocationContext context, Object result, Throwable throwable) {
        final MethodMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final AnnotationAttributes operation = context.annotationAttributes();
        if (isConditionPassing(operation.getString("condition"), metadata, evaluationContext)) {
            final Object key = generateKey(operation.getStringArray("key"), operation.getString("delimiter"), metadata, evaluationContext);
            if (key == null) {
                throw new IllegalArgumentException("the cache action generate null key, action=" + operation);
            }
            final Object field = generateField(operation.getStringArray("field"), operation.getString("delimiter"), metadata, evaluationContext);
            logger.info("==> cache del: key={},field={}", key, field);
            Boolean flag = cache.del(key, field);
            logger.info("<== value: {}", flag);
        }
    }

}
