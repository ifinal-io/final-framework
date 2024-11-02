/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.cache;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import org.ifinalframework.aop.interceptor.AbsOperationInterceptorHandlerSupport;
import org.ifinalframework.cache.annotation.CacheLock;
import org.ifinalframework.cache.annotation.CachePut;
import org.ifinalframework.cache.annotation.Cacheable;
import org.ifinalframework.context.expression.MethodMetadata;
import org.ifinalframework.data.cache.interceptor.DefaultCacheExpressionEvaluator;
import org.ifinalframework.util.Asserts;

import java.util.concurrent.TimeUnit;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsCacheOperationInterceptorHandlerSupport extends AbsOperationInterceptorHandlerSupport
        implements CacheOperationHandlerSupport {

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
    public Object generateKey(@NonNull String[] keys, @NonNull String delimiter, @NonNull MethodMetadata metadata,
                              @NonNull EvaluationContext evaluationContext) {

        return evaluator.key(String.join(delimiter, keys), metadata.getMethodKey(), evaluationContext);
    }

    @Override
    public Object generateField(@NonNull String[] fields, @NonNull String delimiter, @NonNull MethodMetadata metadata,
                                @NonNull EvaluationContext evaluationContext) {

        if (Asserts.isEmpty(fields)) {
            return null;
        }

        return evaluator.field(String.join(delimiter, fields), metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public Object generateValue(@NonNull String value, final MethodMetadata metadata,
                                final EvaluationContext evaluationContext) {

        return evaluator.value(value, metadata.getMethodKey(), evaluationContext);
    }

    @Override
    public <T> T generateValue(@NonNull String value, final MethodMetadata metadata,
                               final EvaluationContext evaluationContext, final Class<T> clazz) {
        return evaluator.value(value, metadata.getMethodKey(), evaluationContext, clazz);
    }

    @Override
    public boolean isConditionPassing(final String condition, final MethodMetadata metadata,
                                      final EvaluationContext evaluationContext) {

        if (this.conditionPassing == null) {
            if (StringUtils.hasText(condition)) {
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
