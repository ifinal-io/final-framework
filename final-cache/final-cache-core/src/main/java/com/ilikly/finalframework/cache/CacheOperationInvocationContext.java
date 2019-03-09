package com.ilikly.finalframework.cache;

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:46:07
 * @since 1.0
 */
public interface CacheOperationInvocationContext {
    @NonNull
    CacheOperation operation();

    @NonNull
    Object target();

    @NonNull
    Method method();

    @NonNull
    Class<?> view();

    @Nullable
    Object[] args();

    Class<?> returnType();

    Type genericReturnType();

    @NonNull
    EvaluationContext createEvaluationContext(@Nullable Object result);

    @Nullable
    Object generateKey(@NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateField(@NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateValue(@NonNull EvaluationContext evaluationContext);

    boolean isConditionPassing(@NonNull EvaluationContext evaluationContext);

    @Nullable
    Object generateExpire(@NonNull EvaluationContext evaluationContext);

    boolean isExpression(String expression);

    @NonNull
    String generateExpression(@NonNull String expression);
}
