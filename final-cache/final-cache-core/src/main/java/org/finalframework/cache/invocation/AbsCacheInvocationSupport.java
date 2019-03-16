package org.finalframework.cache.invocation;


import org.finalframework.cache.CacheInvocationSupport;
import org.finalframework.cache.CacheOperation;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.CacheOperationExpressionEvaluator;
import org.finalframework.cache.interceptor.CacheOperationMetadata;
import org.finalframework.cache.interceptor.DefaultCacheOperationExpressionEvaluator;
import org.finalframework.core.Assert;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 01:22:06
 * @since 1.0
 */
public class AbsCacheInvocationSupport implements CacheInvocationSupport {

    private static final String EXPRESSION_PREFIX = "{";
    private static final String EXPRESSION_SUFFIX = "}";

    private final CacheOperationExpressionEvaluator evaluator;
    private Boolean conditionPassing;

    public AbsCacheInvocationSupport() {
        this.evaluator = new DefaultCacheOperationExpressionEvaluator();
    }

    public AbsCacheInvocationSupport(CacheOperationExpressionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public EvaluationContext createEvaluationContext(CacheOperationContext context, Object result) {
        return evaluator.createEvaluationContext(context.metadata().getMethod(), context.args(),
                context.target(), context.metadata().getTargetClass(), context.metadata().getTargetMethod(), result);
    }

    @Override
    public Object generateKey(Collection<String> keys, String delimiter, CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext) {
        final List<String> keyValues = keys.stream()
                .map(key -> {
                    if (isExpression(key)) {
                        return evaluator.key(generateExpression(key), metadata.getMethodKey(), evaluationContext);
                    } else {
                        return key;
                    }
                })
                .map(Object::toString)
                .collect(Collectors.toList());


        return String.join(delimiter, keyValues);
    }


    @Override
    public Object generateField(Collection<String> fields, String delimiter, CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext) {
        if (Assert.isEmpty(fields)) {
            return null;
        }
        List<String> fieldValues = fields.stream()
                .map(field -> {
                    if (isExpression(field)) {
                        return evaluator.field(generateExpression(field), metadata.getMethodKey(), evaluationContext);
                    } else {
                        return field;
                    }
                })
                .map(Object::toString)
                .collect(Collectors.toList());

        return String.join(delimiter, fieldValues);
    }

    @Override
    public Object generateValue(String value, CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext) {
        if (value != null && isExpression(value)) {
            return evaluator.value(generateExpression(value), metadata.getMethodKey(), evaluationContext);
        }
        return value;
    }

    @Override
    public boolean isConditionPassing(String condition, CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext) {
        if (this.conditionPassing == null) {
            if (condition != null && isExpression(condition)) {
                this.conditionPassing = evaluator.condition(generateExpression(condition), metadata.getMethodKey(), evaluationContext);
            } else {
                this.conditionPassing = true;
            }
        }
        return this.conditionPassing;
    }

    @Override
    public Object generateExpire(String expire, CacheOperationMetadata<? extends CacheOperation> metadata, EvaluationContext evaluationContext) {
        if (expire != null && isExpression(expire)) {
            return evaluator.expired(generateExpression(expire), metadata.getMethodKey(), evaluationContext);
        }
        return null;
    }

    @Override
    public boolean isExpression(String expression) {
        return StringUtils.hasText(expression) && expression.startsWith(EXPRESSION_PREFIX) && expression.endsWith(EXPRESSION_SUFFIX);
    }

    @Override
    public String generateExpression(@NonNull String expression) {
        return expression.substring(EXPRESSION_PREFIX.length(), expression.length() - EXPRESSION_SUFFIX.length());
    }
}
