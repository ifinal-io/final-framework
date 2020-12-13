package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.aop.OperationHandlerSupport;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CacheOperationHandlerSupport extends OperationHandlerSupport {

    @Nullable
    Object generateKey(final @NonNull String[] keys, final @NonNull String delimiter, final @NonNull MethodMetadata metadata,
        final @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateField(final @NonNull String[] fields, final @NonNull String delimiter, final @NonNull MethodMetadata metadata,
        final @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateValue(final @NonNull String value, final @NonNull MethodMetadata metadata, final EvaluationContext evaluationContext);

    @Nullable
    <T> T generateValue(final @NonNull String value, final @NonNull MethodMetadata metadata, final EvaluationContext evaluationContext, final Class<T> clazz);

    boolean isConditionPassing(final @NonNull String condition, final @NonNull MethodMetadata metadata, final EvaluationContext evaluationContext);

    @Nullable
    Object generateExpire(final @NonNull String expire, final @NonNull MethodMetadata metadata, final EvaluationContext evaluationContext);

}
