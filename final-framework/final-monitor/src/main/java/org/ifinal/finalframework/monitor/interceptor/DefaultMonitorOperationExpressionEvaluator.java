package org.ifinal.finalframework.monitor.interceptor;


import org.ifinal.finalframework.aop.interceptor.BaseOperationExpressionEvaluator;
import org.ifinal.finalframework.monitor.MonitorOperationExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultMonitorOperationExpressionEvaluator extends BaseOperationExpressionEvaluator implements MonitorOperationExpressionEvaluator {
    private final Map<ExpressionKey, Expression> nameCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> operatorCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> targetCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> attributeCache = new ConcurrentHashMap<>(64);

    @Override
    public String name(String nameExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.nameCache, methodKey, nameExpression).getValue(evaluationContext).toString();
    }

    @Override
    public Object operator(String operatorExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.operatorCache, methodKey, operatorExpression).getValue(evaluationContext);
    }

    @Override
    public Object target(String targetExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.targetCache, methodKey, targetExpression).getValue(evaluationContext);
    }

    @Override
    public Object attribute(String attributeExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.attributeCache, methodKey, attributeExpression).getValue(evaluationContext);
    }


    @Override
    public void clear() {
        super.clear();
        operatorCache.clear();
        targetCache.clear();
        attributeCache.clear();
    }
}
