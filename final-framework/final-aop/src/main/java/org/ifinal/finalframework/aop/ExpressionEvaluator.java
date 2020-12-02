package org.ifinal.finalframework.aop;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ExpressionEvaluator {

    EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod, Object result, Throwable e);

    Object value(@NonNull String expression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    <T> T value(@NonNull String expression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext, Class<T> clazz);
}
