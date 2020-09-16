package org.finalframework.spring.aop;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 23:02:54
 * @since 1.0
 */
public interface OperationExpressionEvaluator {

    EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod, Object result, Throwable e);

    Object value(@NonNull String expression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    <T> T value(@NonNull String expression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext, Class<T> clazz);
}
