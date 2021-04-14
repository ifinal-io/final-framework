package org.ifinal.finalframework.cache.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;

import org.ifinal.finalframework.aop.interceptor.AbsOperationInterceptorHandlerSupport;
import org.ifinal.finalframework.cache.CacheExpressionEvaluator;
import org.ifinal.finalframework.cache.CacheOperationHandlerSupport;
import org.ifinal.finalframework.cache.annotation.CacheLock;
import org.ifinal.finalframework.cache.annotation.CachePut;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.cache.interceptor.DefaultCacheExpressionEvaluator;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.util.Asserts;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsCacheOperationInterceptorHandlerSupport extends AbsOperationInterceptorHandlerSupport implements
    CacheOperationHandlerSupport {

    private final CacheExpressionEvaluator evaluator;

    private Boolean conditionPassing;

    public AbsCacheOperationInterceptorHandlerSupport() {
        this(new DefaultCacheExpressionEvaluator());
    }

    public AbsCacheOperationInterceptorHandlerSupport(final CacheExpressionEvaluator evaluator) {

        super(evaluator);
        this.evaluator = evaluator;
    }

    protected final String[] getKey(final AnnotationAttributes annotationAttributes) {

        return annotationAttributes.getStringArray("key");
    }

    protected final String[] getField(final AnnotationAttributes annotationAttributes) {

        return annotationAttributes.getStringArray("field");
    }

    protected final String getDelimiter(final AnnotationAttributes annotationAttributes) {

        return annotationAttributes.getString("delimiter");
    }

    protected final String getExpire(final AnnotationAttributes annotationAttributes) {

        return annotationAttributes.getString("expire");
    }

    public final long ttl(final AnnotationAttributes annotationAttributes) {

        return annotationAttributes.getNumber("ttl").longValue();
    }

    /**
     * @param annotationAttributes annotationAttributes
     * @return timeunit
     * @see Cacheable#timeunit()
     * @see CacheLock#timeunit()
     * @see CachePut#timeunit()
     */
    public final TimeUnit timeUnit(final AnnotationAttributes annotationAttributes) {

        return annotationAttributes.getEnum("timeunit");
    }

    @Override
    public Object generateKey(final String[] keys, final String delimiter, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        return evaluator.key(String.join(delimiter, keys), metadata.getMethodKey(), evaluationContext);
    }

    @Override
    public Object generateField(final String[] fields, final String delimiter, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (Asserts.isEmpty(fields)) {
            return null;
        }

        return evaluator.field(String.join(delimiter, fields), metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public Object generateValue(final String value, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        return evaluator.value(value, metadata.getMethodKey(), evaluationContext);
    }

    @Override
    public <T> T generateValue(final String value, final MethodMetadata metadata,
        final EvaluationContext evaluationContext, final Class<T> clazz) {
        return evaluator.value(value, metadata.getMethodKey(), evaluationContext, clazz);
    }

    @Override
    public boolean isConditionPassing(final String condition, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (this.conditionPassing == null) {
            if (condition != null) {
                this.conditionPassing = evaluator.condition(condition, metadata.getMethodKey(), evaluationContext);
            } else {
                this.conditionPassing = true;
            }
        }
        return this.conditionPassing;
    }

    @Override
    public Object generateExpire(final String expire, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (expire != null) {
            return evaluator.expired(expire, metadata.getMethodKey(), evaluationContext);
        }
        return null;
    }

}
