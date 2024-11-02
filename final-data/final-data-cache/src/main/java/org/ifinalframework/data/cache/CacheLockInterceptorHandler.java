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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.cache.annotation.Cache;
import org.ifinalframework.cache.annotation.CacheLock;
import org.ifinalframework.context.expression.MethodMetadata;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.util.Dates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author iimik
 * @version 1.0.0
 * @see CacheLock
 * @since 1.0.0
 */
@Component
public class CacheLockInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport implements
        CacheInterceptorHandler {

    private static final String KEY = "key";

    private static final String VALUE = "value";

    private static final String LOCK = "lock";

    @Override
    public Object before(final @NonNull Cache cache, final @NonNull InvocationContext context,
                         final @NonNull AnnotationAttributes annotation) {

        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);
        final MethodMetadata metadata = context.metadata();
        final Object key = generateKey(annotation.getStringArray("key"), annotation.getString("delimiter"), metadata,
                evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + annotation);
        }
        Object value = Asserts.isEmpty(annotation.getString(VALUE))
                ? key : generateValue(annotation.getString(VALUE), metadata, evaluationContext);

        if (Objects.isNull(value)) {
            value = key;
        }

        // find the ttl form expire and ttl
        Long ttl = ttl(annotation, metadata, evaluationContext);
        TimeUnit timeUnit = annotation.getEnum("timeUnit");

        final long sleep = annotation.getNumber("sleep");
        context.addAttribute(KEY, key);
        context.addAttribute(VALUE, value);

        int retry = 0;
        int maxRetry = annotation.getNumber("retry").intValue();
        do {
            logger
                    .info("==> try to lock: key={},value={},ttl={},timeUnit={},retry={}", key, value, ttl, timeUnit, retry);
            final boolean lock = cache.lock(key, value, ttl, timeUnit);
            logger.info("<== lock result: {}", lock);
            if (lock) {
                context.addAttribute(LOCK, true);
                return null;
            }

            trySleep(sleep);

            retry++;
        } while (retry <= maxRetry);

        context.addAttribute(LOCK, false);
        throw new CacheLockException(String.format("failure to lock key=%s,value=%s", key, value));
    }

    private void trySleep(final long sleep) {
        if (sleep > 0L) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Long ttl(final AnnotationAttributes annotation, final MethodMetadata metadata,
                     final EvaluationContext evaluationContext) {

        Object expired = generateExpire(annotation.getString("expire"), metadata, evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                return ((Date) expired).getTime() - System.currentTimeMillis();
            } else if (expired instanceof LocalDateTime) {
                Date date = Dates.to((LocalDateTime) expired);
                Objects.requireNonNull(date);
                return date.getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            return annotation.getNumber("ttl");
        }
    }

    @Override
    public void after(final @NonNull Cache cache, final @NonNull InvocationContext context,
                      final @NonNull AnnotationAttributes annotation,
                      final @Nullable Object result, final @Nullable Throwable throwable) {

        final Logger logger = LoggerFactory.getLogger(context.target().getClass());

        final Object key = context.getAttribute(KEY);
        final Object value = context.getAttribute(VALUE);
        final Boolean lock = context.getAttribute(LOCK);

        if (Boolean.TRUE.equals(lock)) {
            logger.info("==> try to unlock: key={},value={}", key, value);
            final boolean unlock = cache.unlock(key, value);
            logger.info("<== result: {}", unlock);
        }
    }

}
