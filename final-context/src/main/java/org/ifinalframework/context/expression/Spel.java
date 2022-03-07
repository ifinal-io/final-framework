/*
 * Copyright 2020-2022 the original author or authors.
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

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @see SpelExpressionParser
 * @since 1.0.0
 */
@Slf4j
public final class Spel {

    private static final ExpressionParser PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));
    private static final SpelComparator COMPARATOR = new SpelComparator();

    /**
     * {@link MapAccessor}支持使用对象表达式{@code a.b}替代取值表达式{@code a['b']}。
     */
    private static final MapAccessor MAP_ACCESSOR = new PropertyMapAccessor();

    /**
     * 以{@code #{}}包裹的才算表达式。
     */
    private static final ParserContext PARSER_CONTEXT = ParserContext.TEMPLATE_EXPRESSION;

    private Spel() {
        throw new IllegalAccessError("Spel is not support new instance for you!");
    }

    static EvaluationContext wrapContext(Object context) {
        if (context instanceof EvaluationContext) {
            return (EvaluationContext) context;
        }

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
        evaluationContext.addPropertyAccessor(MAP_ACCESSOR);
        return evaluationContext;

    }

    public static Object getValue(final String expression) {
        return expression(expression, PARSER_CONTEXT).getValue();
    }

    public static Object getValue(final String expression, final Object context) {
        return expression(expression, PARSER_CONTEXT).getValue(wrapContext(context));
    }

    public static <T> T getValue(final String expression, final Object context, final Class<T> type) {
        return expression(expression, PARSER_CONTEXT).getValue(wrapContext(context), type);
    }

    public static void setValue(final String expression, final Object context, final Object value) {
        if (context instanceof EvaluationContext) {
            PARSER.parseExpression(expression).setValue((EvaluationContext) context, value);
        } else {
            PARSER.parseExpression(expression).setValue(context, value);
        }
    }

    public static List<ExpressionItem> compare(@NonNull Object leftObj, @NonNull Object rightObj, @NonNull Collection<String> expressions) {
        return COMPARATOR.compare(leftObj, rightObj, expressions);
    }

    public static boolean equals(@Nullable Object leftObj, @Nullable Object rightObj, @NonNull Collection<String> expressions) {
        if (Objects.isNull(leftObj) && Objects.isNull(rightObj)) return true;
        if (Objects.isNull(leftObj) || Objects.isNull(rightObj)) return false;

        List<ExpressionItem> list = compare(leftObj, rightObj, expressions);

        for (ExpressionItem expressionItem : list) {
            if (expressionItem.equals()) {
                continue;
            }
            return false;
        }

        return true;

    }

    static Expression expression(final String expression, final ParserContext context) {
        return PARSER.parseExpression(expression, context);
    }


}

