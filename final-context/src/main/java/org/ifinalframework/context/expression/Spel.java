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
import org.springframework.expression.*;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * SpEL(Spring Expression Language)
 * @author ilikly
 * @version 1.0.0
 * @see SpelExpressionParser
 * @since 1.0.0
 */
@Slf4j
public final class Spel {

    private static final ExpressionParser PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));

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


    /**
     * @see #equals(Object, Object, Collection, CompareListener)
     * @since 1.3.1
     */
    public static boolean equals(@Nullable Object leftObj, @Nullable Object rightObj, @NonNull Collection<String> expressions) throws EvaluationException {
        return equals(leftObj, rightObj, expressions, null);
    }

    /**
     * 比较两个对象在取值表达式下是否相等。
     *
     * @param leftObj     左对象
     * @param rightObj    右对象
     * @param expressions 表达式集合
     * @param listener    比较监听
     * @return 相等返回{@code true}，否则返回{@code false}。
     * @throws EvaluationException 解析表达式异常
     * @since 1.3.1
     */
    public static boolean equals(@Nullable Object leftObj, @Nullable Object rightObj, @NonNull Collection<String> expressions, @Nullable CompareListener listener) throws EvaluationException {
        if (Objects.isNull(leftObj) && Objects.isNull(rightObj)) return true;
        if (Objects.isNull(leftObj) || Objects.isNull(rightObj)) return false;

        EvaluationContext leftContext = Spel.wrapContext(leftObj);
        EvaluationContext rightContext = Spel.wrapContext(rightObj);

        Object leftValue = null;
        Object rightValue = null;
        for (String expression : expressions) {

            Expression express = Spel.expression(expression, ParserContext.TEMPLATE_EXPRESSION);
            leftValue = express.getValue(leftContext);
            rightValue = express.getValue(rightContext);

            boolean equals = ObjectUtils.nullSafeEquals(leftValue, rightValue);

            if (Objects.nonNull(listener)) {
                listener.onCompare(expression, leftValue, rightValue, equals);
            }

            if (!equals) {
                return false;
            }

        }

        return true;

    }

    static Expression expression(final String expression, final ParserContext context) {
        return PARSER.parseExpression(expression, context);
    }

    @FunctionalInterface
    public interface CompareListener {
        void onCompare(@NonNull String expression, @Nullable Object leftValue, @Nullable Object rightValue, boolean equals);
    }


}

