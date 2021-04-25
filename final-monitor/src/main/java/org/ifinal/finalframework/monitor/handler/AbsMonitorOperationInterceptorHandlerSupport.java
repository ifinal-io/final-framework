package org.ifinal.finalframework.monitor.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;

import org.ifinal.finalframework.aop.interceptor.AbsOperationInterceptorHandlerSupport;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.monitor.MonitorExpressionEvaluator;
import org.ifinal.finalframework.monitor.MonitorOperationHandlerSupport;
import org.ifinal.finalframework.monitor.annotation.MonitorLevel;
import org.ifinal.finalframework.monitor.interceptor.DefaultMonitorExpressionEvaluator;
import org.ifinal.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsMonitorOperationInterceptorHandlerSupport extends AbsOperationInterceptorHandlerSupport implements
    MonitorOperationHandlerSupport {

    private final MonitorExpressionEvaluator evaluator;

    public AbsMonitorOperationInterceptorHandlerSupport() {
        this(new DefaultMonitorExpressionEvaluator());
    }

    public AbsMonitorOperationInterceptorHandlerSupport(final MonitorExpressionEvaluator evaluator) {

        super(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public String generateName(final String[] name, final String delimiter, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        return evaluator.name(String.join(delimiter, name), metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public Object generateOperator(final String operator, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (Asserts.nonBlank(operator)) {
            return evaluator.operator(operator, metadata.getMethodKey(), evaluationContext);
        }

        return UserContextHolder.getUser();
    }

    @Override
    public Object generateTarget(final String target, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (Asserts.isBlank(target)) {
            return null;
        }
        return evaluator.target(target, metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public Object generateAttribute(final String attribute, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (Asserts.isBlank(attribute)) {
            return null;
        }

        return evaluator.attribute(attribute, metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public MonitorLevel level(final AnnotationAttributes annotation) {
        return annotation.getEnum("level");
    }

}
