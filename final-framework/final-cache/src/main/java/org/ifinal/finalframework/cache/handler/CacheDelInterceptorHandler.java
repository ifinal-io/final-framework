package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheDelInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport {

    @Override
    protected void doHandle(@NonNull Cache cache, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation,
                            @Nullable Object result, @Nullable Throwable throwable) {
        final MethodMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        if (isConditionPassing(annotation.getString("condition"), metadata, evaluationContext)) {
            final Object key = generateKey(annotation.getStringArray("key"), annotation.getString("delimiter"), metadata, evaluationContext);
            if (key == null) {
                throw new IllegalArgumentException("the cache action generate null key, action=" + annotation);
            }
            final Object field = generateField(annotation.getStringArray("field"), annotation.getString("delimiter"), metadata, evaluationContext);
            logger.info("==> cache del: key={},field={}", key, field);
            Boolean flag = cache.del(key, field);
            logger.info("<== value: {}", flag);
        }
    }

}
