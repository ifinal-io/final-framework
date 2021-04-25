package org.ifinal.finalframework.aop.interceptor;

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import org.ifinal.finalframework.aop.ExpressionEvaluator;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.OperationHandlerSupport;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsOperationInterceptorHandlerSupport implements OperationHandlerSupport {

    private final ExpressionEvaluator evaluator;

    public AbsOperationInterceptorHandlerSupport(final ExpressionEvaluator evaluator) {

        this.evaluator = evaluator;
    }

    @Override
    @NonNull
    public EvaluationContext createEvaluationContext(final @NonNull InvocationContext context, final Object result,
        final Throwable e) {

        return evaluator.createEvaluationContext(context.metadata().getMethod(), context.args(),
            context.target(), context.metadata().getTargetClass(), context.metadata().getTargetMethod(), result, e);

    }

}
