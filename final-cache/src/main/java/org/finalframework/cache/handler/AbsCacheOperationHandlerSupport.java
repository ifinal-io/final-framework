

package org.finalframework.cache.handler;


import org.finalframework.cache.CacheOperationExpressionEvaluator;
import org.finalframework.cache.CacheOperationHandlerSupport;
import org.finalframework.cache.interceptor.DefaultCacheOperationExpressionEvaluator;
import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationMetadata;
import org.finalframework.spring.aop.interceptor.AbsOperationHandlerSupport;
import org.springframework.expression.EvaluationContext;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 01:22:06
 * @since 1.0
 */
public class AbsCacheOperationHandlerSupport extends AbsOperationHandlerSupport implements CacheOperationHandlerSupport {

    private final CacheOperationExpressionEvaluator evaluator;
    private Boolean conditionPassing;

    public AbsCacheOperationHandlerSupport() {
        this(new DefaultCacheOperationExpressionEvaluator());
    }

    public AbsCacheOperationHandlerSupport(CacheOperationExpressionEvaluator evaluator) {
        super(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public Object generateKey(Collection<String> keys, String delimiter, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
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
    public Object generateField(Collection<String> fields, String delimiter, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
        if (Asserts.isEmpty(fields)) {
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
    public Object generateValue(String value, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
        if (value != null && isExpression(value)) {
            return evaluator.value(generateExpression(value), metadata.getMethodKey(), evaluationContext);
        }
        return value;
    }

    @Override
    public <T> T generateValue(String value, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext, Class<T> clazz) {
        if (value != null && isExpression(value)) {
            return evaluator.value(generateExpression(value), metadata.getMethodKey(), evaluationContext, clazz);
        }
        throw new IllegalArgumentException("value expression can not evaluator to " + clazz.getCanonicalName());
    }

    @Override
    public boolean isConditionPassing(String condition, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
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
    public Object generateExpire(String expire, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
        if (expire != null && isExpression(expire)) {
            return evaluator.expired(generateExpression(expire), metadata.getMethodKey(), evaluationContext);
        }
        return null;
    }

}