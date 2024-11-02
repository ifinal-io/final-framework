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

package org.ifinalframework.data.cache.interceptor;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import org.ifinalframework.aop.interceptor.BaseExpressionEvaluator;
import org.ifinalframework.data.cache.CacheExpressionEvaluator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultCacheExpressionEvaluator extends BaseExpressionEvaluator implements CacheExpressionEvaluator {

    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);

    private final Map<ExpressionKey, Expression> fieldCache = new ConcurrentHashMap<>(64);

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);

    private final Map<ExpressionKey, Expression> expiredCache = new ConcurrentHashMap<>(64);

    @Override
    public Object key(final String keyExpression, final AnnotatedElementKey methodKey,
                      final EvaluationContext context) {

        return getExpression(this.keyCache, methodKey, keyExpression).getValue(context);
    }

    @Override
    public Object field(final String fieldExpression, final AnnotatedElementKey methodKey,
                        final EvaluationContext context) {

        return getExpression(this.fieldCache, methodKey, fieldExpression).getValue(context);
    }

    @Override
    public boolean condition(final String conditionExpression, final AnnotatedElementKey methodKey,
                             final EvaluationContext context) {

        return Boolean.TRUE.equals(getExpression(this.conditionCache, methodKey, conditionExpression).getValue(
                context, Boolean.class));
    }

    @Override
    public Object expired(final String expiredExpression, final AnnotatedElementKey methodKey,
                          final EvaluationContext context) {

        return getExpression(this.expiredCache, methodKey, expiredExpression).getValue(context);
    }

    @Override
    public void clear() {
        super.clear();
        this.keyCache.clear();
        this.fieldCache.clear();
        this.conditionCache.clear();
        this.expiredCache.clear();
    }

}
