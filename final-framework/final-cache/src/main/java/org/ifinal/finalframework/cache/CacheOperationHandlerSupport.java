package org.ifinal.finalframework.cache;


import org.ifinal.finalframework.aop.OperationHandlerSupport;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CacheOperationHandlerSupport extends OperationHandlerSupport {

    @Nullable
    Object generateKey(@NonNull Collection<String> keys, @NonNull String delimiter, @NonNull MethodMetadata metadata, @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateField(@NonNull Collection<String> fields, @NonNull String delimiter, @NonNull MethodMetadata metadata, @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateValue(@NonNull String value, @NonNull MethodMetadata metadata, EvaluationContext evaluationContext);

    @Nullable
    <T> T generateValue(@NonNull String value, @NonNull MethodMetadata metadata, EvaluationContext evaluationContext, Class<T> clazz);

    boolean isConditionPassing(@NonNull String condition, @NonNull MethodMetadata metadata, EvaluationContext evaluationContext);

    @Nullable
    Object generateExpire(@NonNull String expire, @NonNull MethodMetadata metadata, EvaluationContext evaluationContext);

}
