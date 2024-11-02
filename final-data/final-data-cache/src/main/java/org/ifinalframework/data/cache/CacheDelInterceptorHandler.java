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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.cache.annotation.Cache;
import org.ifinalframework.cache.annotation.CacheDel;
import org.ifinalframework.context.expression.MethodMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Del the cache with {@link CacheDel#key()} and {@link CacheDel#field()} when {@link CacheDel#condition()} is passing.
 *
 * @author iimik
 * @version 1.0.0
 * @see CacheDel
 * @see Cache#del(Object, Object)
 * @since 1.0.0
 */
@Component
public class CacheDelInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport implements
        CacheInterceptorHandler {

    @Override
    public void handle(final @NonNull Cache cache, final @NonNull InvocationContext context,
                       final @NonNull AnnotationAttributes annotation,
                       final @Nullable Object result, final @Nullable Throwable throwable) {

        final MethodMetadata metadata = context.metadata();
        final Logger logger = LoggerFactory.getLogger(metadata.getTargetClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);

        if (isConditionPassing(annotation.getString("condition"), metadata, evaluationContext)) {
            final Object key = generateKey(annotation.getStringArray("key"), annotation.getString("delimiter"),
                    metadata, evaluationContext);
            if (key == null) {
                throw new IllegalArgumentException("the cache action generate null key, action=" + annotation);
            }
            final Object field = generateField(annotation.getStringArray("field"), annotation.getString("delimiter"),
                    metadata, evaluationContext);

            logger.info("==> cache del: key={},field={}", key, field);
            Boolean flag = cache.del(key, field);
            logger.info("<== cache del result: key={},field={},result={}", key, field, flag);
        }
    }

}
