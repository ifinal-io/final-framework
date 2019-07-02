package org.finalframework.cache;


import org.finalframework.spring.aop.InvocationSupport;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationMetadata;
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
public interface CacheInvocationSupport extends InvocationSupport {

    @Nullable
    Object generateKey(@NonNull Collection<String> keys, @NonNull String delimiter, @NonNull OperationMetadata<? extends Operation> metadata, @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateField(@NonNull Collection<String> fields, @NonNull String delimiter, @NonNull OperationMetadata<? extends Operation> metadata, @NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateValue(@NonNull String value, @NonNull OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext);

    @Nullable
    <T> T generateValue(@NonNull String value, @NonNull OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext, Class<T> clazz);

    boolean isConditionPassing(@NonNull String condition, @NonNull OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext);

    @Nullable
    Object generateExpire(@NonNull String expire, @NonNull OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext);

}
