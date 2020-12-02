package org.ifinal.finalframework.monitor.handler;


import org.ifinal.finalframework.annotation.monitor.MonitorLevel;
import org.ifinal.finalframework.aop.interceptor.AbsOperationInterceptorHandlerSupport;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.monitor.MonitorOperationExpressionEvaluator;
import org.ifinal.finalframework.monitor.MonitorOperationHandlerSupport;
import org.ifinal.finalframework.monitor.interceptor.DefaultMonitorOperationExpressionEvaluator;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsMonitorOperationInterceptorHandlerSupport<E> extends AbsOperationInterceptorHandlerSupport<E> implements MonitorOperationHandlerSupport {
    private final MonitorOperationExpressionEvaluator evaluator;

    public AbsMonitorOperationInterceptorHandlerSupport() {
        this(new DefaultMonitorOperationExpressionEvaluator());
    }

    public AbsMonitorOperationInterceptorHandlerSupport(MonitorOperationExpressionEvaluator evaluator) {
        super(evaluator);
        this.evaluator = evaluator;
    }


    @Override
    public String generateName(String[] name, String delimiter, MethodMetadata metadata, EvaluationContext evaluationContext) {
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
    public Object generateOperator(String operator, MethodMetadata metadata, EvaluationContext evaluationContext) {
        if (Asserts.nonBlank(operator)) {
            if (isExpression(operator)) {
                return evaluator.operator(generateExpression(operator), metadata.getMethodKey(), evaluationContext);
            }
            return operator;
        }

        return UserContextHolder.getUser();
    }

    @Override
    public Object generateTarget(String target, MethodMetadata metadata, EvaluationContext evaluationContext) {
        if (Asserts.isBlank(target)) return null;
        if (isExpression(target)) {
            return evaluator.target(generateExpression(target), metadata.getMethodKey(), evaluationContext);
        }
        return target;
    }

    @Override
    public Object generateAttribute(String attribute, MethodMetadata metadata, EvaluationContext evaluationContext) {
        if (Asserts.nonBlank(attribute) && isExpression(attribute)) {
            return evaluator.attribute(generateExpression(attribute), metadata.getMethodKey(), evaluationContext);
        }
        return attribute;
    }

    @Override
    public MonitorLevel level(AnnotationAttributes annotation) {
        return annotation.getEnum("level");
    }


}
