/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.context.expression;


import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * DefaultEvaluationContextFactory
 *
 * @author iimik
 * @since 1.5.6
 **/
public class DefaultEvaluationContextFactory implements EvaluationContextFactory{

    /**
     * {@link MapAccessor}支持使用对象表达式{@code a.b}替代取值表达式{@code a['b']}。
     */
    private static final MapAccessor MAP_ACCESSOR = new PropertyMapAccessor();

    @NonNull
    @Override
    public EvaluationContext create(@Nullable Object object) {
        if (object instanceof EvaluationContext context) {
            return context;
        }

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(object);
        evaluationContext.addPropertyAccessor(MAP_ACCESSOR);

        evaluationContext.addPropertyAccessor(new BeanFactoryAccessor());

        return evaluationContext;
    }
}
