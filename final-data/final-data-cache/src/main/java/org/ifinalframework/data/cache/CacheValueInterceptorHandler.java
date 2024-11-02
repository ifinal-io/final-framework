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
import org.springframework.stereotype.Component;

import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.cache.annotation.Cache;
import org.ifinalframework.cache.annotation.Cacheable;
import org.ifinalframework.json.Json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * @author iimik
 * @version 1.0.0
 * @see Cacheable
 * @see Cache#get(Object, Object, Type, Class)
 * @since 1.0.0
 */
@SuppressWarnings("all")
@Component
public class CacheValueInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport implements
        CacheInterceptorHandler {

    @Override
    public Object before(final Cache cache, final InvocationContext context, final AnnotationAttributes operation) {

        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);

        String delimiter = operation.getString("delimiter");
        final Object key = generateKey(operation.getStringArray("key"), delimiter, context.metadata(),
                evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + context);
        }
        final Object field = generateField(operation.getStringArray("field"), delimiter, context.metadata(),
                evaluationContext);
        final Type type = (Type) operation.get("parameterType");
        logger.info("==> cache get: key={},field={}", key, field);
        Object cacheValue = cache.get(key, field, type, null);
        logger.info("<== value: {}", Json.toJson(cacheValue));
        context.args()[(int) operation.getNumber("parameterIndex")] = cacheValue;

        return null;
    }

}
