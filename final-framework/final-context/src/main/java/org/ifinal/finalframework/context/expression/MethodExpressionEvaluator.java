package org.ifinal.finalframework.context.expression;

import java.lang.reflect.Method;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MethodExpressionEvaluator extends CachedExpressionEvaluator {

    /**
     * Indicate that there is no value variable.
     */
    public static final Object NO_RESULT = new Object();

    /**
     * Indicate that the value variable cannot be used at all.
     */
    public static final Object RESULT_UNAVAILABLE = new Object();

    /**
     * The name of the variable holding the value object.
     */
    private static final String RESULT_VARIABLE = "result";

    private static final String THROWABLE_VARIABLE = "e";

    public EvaluationContext createEvaluationContext(final Method method, final Object[] args, final Object target,
        final Class<?> targetClass, final Method targetMethod,
        final Object result, final Throwable e) {

        final MethodExpressionRootObject rootObject = new MethodExpressionRootObject(method, args, target, targetClass);
        final MethodEvaluationContext evaluationContext = new MethodEvaluationContext(rootObject, targetMethod, args,
            getParameterNameDiscoverer());
        if (result == RESULT_UNAVAILABLE) {
            evaluationContext.addUnavailableVariable(RESULT_VARIABLE);
        } else if (result != NO_RESULT) {
            evaluationContext.setVariable(RESULT_VARIABLE, result);
        }

        if (e != null) {
            evaluationContext.setVariable(THROWABLE_VARIABLE, e);
        }

        return evaluationContext;
    }

}
