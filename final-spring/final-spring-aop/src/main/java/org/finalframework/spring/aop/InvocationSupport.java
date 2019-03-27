package org.finalframework.spring.aop;

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 20:57:40
 * @since 1.0
 */
public interface InvocationSupport {

    @NonNull
    EvaluationContext createEvaluationContext(@NonNull OperationContext context, @Nullable Object result, @Nullable Throwable e);

    boolean isExpression(String expression);

    @NonNull
    String generateExpression(@NonNull String expression);
}
