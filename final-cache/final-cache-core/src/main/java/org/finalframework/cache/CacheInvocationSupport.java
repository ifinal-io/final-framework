package org.finalframework.cache;


import org.finalframework.cache.interceptor.CacheOperationMetadata;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 01:20:17
 * @since 1.0
 */
public interface CacheInvocationSupport {

    @NonNull
    EvaluationContext createEvaluationContext(@NonNull CacheOperationContext context, @Nullable Object result);

    @Nullable
    Object generateKey(@NonNull Collection<String> keys, @NonNull String delimiter, @NonNull CacheOperationMetadata<? extends CacheOperation> metadata, @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateField(@NonNull Collection<String> fields, @NonNull String delimiter, @NonNull CacheOperationMetadata<? extends CacheOperation> metadata, @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateValue(@NonNull String value, @NonNull CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext);

    boolean isConditionPassing(@NonNull String condition, @NonNull CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext);

    @Nullable
    Object generateExpire(@NonNull String expire, @NonNull CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext);

    boolean isExpression(String expression);

    @NonNull
    String generateExpression(@NonNull String expression);

}
