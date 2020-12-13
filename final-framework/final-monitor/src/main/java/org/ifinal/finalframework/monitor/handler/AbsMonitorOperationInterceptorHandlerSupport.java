package org.ifinal.finalframework.monitor.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.ifinal.finalframework.annotation.monitor.MonitorLevel;
import org.ifinal.finalframework.aop.interceptor.AbsOperationInterceptorHandlerSupport;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.monitor.MonitorExpressionEvaluator;
import org.ifinal.finalframework.monitor.MonitorOperationHandlerSupport;
import org.ifinal.finalframework.monitor.interceptor.DefaultMonitorExpressionEvaluator;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsMonitorOperationInterceptorHandlerSupport extends AbsOperationInterceptorHandlerSupport implements MonitorOperationHandlerSupport {

    private final MonitorExpressionEvaluator evaluator;

    public AbsMonitorOperationInterceptorHandlerSupport() {
        this(new DefaultMonitorExpressionEvaluator());
    }

    public AbsMonitorOperationInterceptorHandlerSupport(final MonitorExpressionEvaluator evaluator) {

        super(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public String generateName(final String[] name, final String delimiter, final MethodMetadata metadata, final EvaluationContext evaluationContext) {

        final List<String> nameValues = Arrays.stream(name)
            .map(key -> {
                if (isExpression(key)) {
                    return evaluator.name(generateExpression(key), metadata.getMethodKey(), evaluationContext);
                } else {
                    return key;
                }
            })
            .map(Object::toString)
            .collect(Collectors.toList());

        return String.join(delimiter, nameValues);
    }

    @Override
    public Object generateOperator(final String operator, final MethodMetadata metadata, final EvaluationContext evaluationContext) {

        if (Asserts.nonBlank(operator)) {
            if (isExpression(operator)) {
                return evaluator.operator(generateExpression(operator), metadata.getMethodKey(), evaluationContext);
            }
            return operator;
        }

        return UserContextHolder.getUser();
    }

    @Override
    public Object generateTarget(final String target, final MethodMetadata metadata, final EvaluationContext evaluationContext) {

        if (Asserts.isBlank(target)) {
            return null;
        }
        if (isExpression(target)) {
            return evaluator.target(generateExpression(target), metadata.getMethodKey(), evaluationContext);
        }
        return target;
    }

    @Override
    public Object generateAttribute(final String attribute, final MethodMetadata metadata, final EvaluationContext evaluationContext) {

        if (Asserts.nonBlank(attribute) && isExpression(attribute)) {
            return evaluator.attribute(generateExpression(attribute), metadata.getMethodKey(), evaluationContext);
        }
        return attribute;
    }

    @Override
    public MonitorLevel level(final AnnotationAttributes annotation) {

        return annotation.getEnum("level");
    }

}
