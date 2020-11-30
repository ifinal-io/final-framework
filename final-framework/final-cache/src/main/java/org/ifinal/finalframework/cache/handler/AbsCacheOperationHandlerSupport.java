package org.ifinal.finalframework.cache.handler;


import org.ifinal.finalframework.aop.interceptor.AbsOperationHandlerSupport;
import org.ifinal.finalframework.cache.CacheOperationExpressionEvaluator;
import org.ifinal.finalframework.cache.CacheOperationHandlerSupport;
import org.ifinal.finalframework.cache.annotation.CacheLock;
import org.ifinal.finalframework.cache.annotation.CachePut;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.cache.interceptor.DefaultCacheOperationExpressionEvaluator;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

    protected final String[] getKey(AnnotationAttributes annotationAttributes) {
        return annotationAttributes.getStringArray("key");
    }

    protected final String[] getField(AnnotationAttributes annotationAttributes) {
        return annotationAttributes.getStringArray("field");
    }

    protected final String getDelimiter(AnnotationAttributes annotationAttributes) {
        return annotationAttributes.getString("delimiter");
    }

    protected final String getExpire(AnnotationAttributes annotationAttributes) {
        return annotationAttributes.getString("expire");
    }

    public final long ttl(AnnotationAttributes annotationAttributes) {
        return annotationAttributes.getNumber("ttl").longValue();
    }

    /**
     * @param annotationAttributes
     * @return
     * @see Cacheable#timeunit()
     * @see CacheLock#timeunit()
     * @see CachePut#timeunit()
     */
    public final TimeUnit timeUnit(AnnotationAttributes annotationAttributes) {
        return annotationAttributes.getEnum("timeunit");
    }


    @Override
    public Object generateKey(String[] keys, String delimiter, MethodMetadata metadata, EvaluationContext evaluationContext) {
        final List<String> keyValues = Arrays.stream(keys)
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
    public Object generateField(String[] fields, String delimiter, MethodMetadata metadata, EvaluationContext evaluationContext) {
        if (Asserts.isEmpty(fields)) {
            return null;
        }
        List<String> fieldValues = Arrays.stream(fields)
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
    public Object generateValue(String value, MethodMetadata metadata, EvaluationContext evaluationContext) {
        if (value != null && isExpression(value)) {
            return evaluator.value(generateExpression(value), metadata.getMethodKey(), evaluationContext);
        }
        return value;
    }

    @Override
    public <T> T generateValue(String value, MethodMetadata metadata, EvaluationContext evaluationContext, Class<T> clazz) {
        if (value != null && isExpression(value)) {
            return evaluator.value(generateExpression(value), metadata.getMethodKey(), evaluationContext, clazz);
        }
        throw new IllegalArgumentException("value expression can not evaluator to " + clazz.getCanonicalName());
    }

    @Override
    public boolean isConditionPassing(String condition, MethodMetadata metadata, EvaluationContext evaluationContext) {
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
    public Object generateExpire(String expire, MethodMetadata metadata, EvaluationContext evaluationContext) {
        if (expire != null && isExpression(expire)) {
            return evaluator.expired(generateExpression(expire), metadata.getMethodKey(), evaluationContext);
        }
        return null;
    }

}
