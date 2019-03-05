package com.ilikly.finalframework.cache;

import org.springframework.expression.EvaluationContext;

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

    Object generateKey(EvaluationContext result);

    Object generateField(EvaluationContext result);

    Object generateResult(EvaluationContext result);

    boolean isConditionPassing(EvaluationContext result);

    Object generateExpire(EvaluationContext result);
}
