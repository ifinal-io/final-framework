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

    EvaluationContext createEvaluationContext(final Method method, final Object[] args, final Object target, final Class<?> targetClass, final Method targetMethod, final Object result, final Throwable e);

    Object value(final @NonNull String expression, final AnnotatedElementKey methodKey, final EvaluationContext evaluationContext);

    <T> T value(final @NonNull String expression, final AnnotatedElementKey methodKey, final EvaluationContext evaluationContext, Class<T> clazz);
}
