package com.ilikly.finalframework.cache;

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:46:07
 * @since 1.0
 */
public interface CacheOperationInvocationContext {
    CacheOperation operation();

    Object target();

    Method method();

    Object[] args();

    Class<?> view();

    Class<?> returnType();

    Type genericReturnType();

    EvaluationContext createEvaluationContext(Object result);

    Object generateKey(EvaluationContext result);

    Object generateField(EvaluationContext result);

    Object generateValue(EvaluationContext result);

    boolean isConditionPassing(EvaluationContext result);

    Object generateExpire(EvaluationContext result);

    boolean isExpression(String expression);

    String generateExpression(@NonNull String expression);
}
