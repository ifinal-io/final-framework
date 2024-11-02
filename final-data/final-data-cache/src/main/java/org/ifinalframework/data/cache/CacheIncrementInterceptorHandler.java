/*
 * Copyright 2020-2021 the original author or authors.
 *
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
import org.springframework.stereotype.Component;

import org.ifinalframework.aop.InterceptorHandler;
import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.cache.annotation.Cache;
import org.ifinalframework.util.Primaries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheIncrementInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport implements
        InterceptorHandler<Cache, AnnotationAttributes> {

    @Override
    public void handle(final @NonNull Cache cache, final @NonNull InvocationContext context,
                       final @NonNull AnnotationAttributes annotation, final Object result, final Throwable throwable) {

        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        final Object key = generateKey(getKey(annotation), getDelimiter(annotation), context.metadata(),
                evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + context);
        }
        final Object field = generateField(getField(annotation), getDelimiter(annotation), context.metadata(),
                evaluationContext);

        final boolean hasKey = cache.isExists(key, field);

        boolean incremented = Objects
                .nonNull(doIncrement(logger, cache, context, annotation, key, field, evaluationContext));

        if (!hasKey && incremented) {
            long ttl;
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            Object expired = generateExpire(getExpire(annotation), context.metadata(), evaluationContext);

            if (expired != null) {
                if (expired instanceof Date) {
                    ttl = ((Date) expired).getTime() - System.currentTimeMillis();
                } else {
                    throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
                }
            } else {
                ttl = ttl(annotation);
                timeUnit = timeUnit(annotation);
            }

            if (ttl > 0) {
                cache.expire(key, ttl, timeUnit);
            }

        }

    }

    private Number doIncrement(final Logger logger, final Cache cache, final InvocationContext context,
                               final AnnotationAttributes annotation, final Object key, final Object field,
                               final EvaluationContext evaluationContext) {

        final Class<? extends Number> type = annotation.getClass("type");

        if (Primaries.isLong(type) || Primaries.isInteger(type)) {
            final Long value = generateValue(annotation.getString("value"), context.metadata(), evaluationContext,
                    Long.class);
            if (value == null) {
                throw new NullPointerException("increment value is null");
            }
            logger.info("==> cache increment: key={}, field={}, value={}", key, field, value);
            final Long increment = cache.increment(key, field, value);
            logger.info("<== result: {}", increment);
            return increment;
        } else if (Primaries.isFloat(type) || Primaries.isDouble(type)) {
            final Double value = generateValue(annotation.getString("value"), context.metadata(), evaluationContext,
                    Double.class);
            if (value == null) {
                throw new NullPointerException("increment value is null");
            }
            logger.info("==> cache increment: key={}, field={}, value={}", key, field, value);
            final Double increment = cache.increment(key, field, value);
            logger.info("<== result: {}", increment);
            return increment;
        } else {
            throw new IllegalArgumentException(
                    String.format("CacheIncrementOperation value type %s is not support.", type.getCanonicalName()));
        }
    }

}
