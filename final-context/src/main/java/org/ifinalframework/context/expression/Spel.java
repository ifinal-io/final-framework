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

package org.ifinalframework.context.expression;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Spel {

    private static final ExpressionParser PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));

    private static final MapAccessor MAP_ACCESSOR = new PropertyMapAccessor();

    private static final ParserContext PARSER_CONTEXT = ParserContext.TEMPLATE_EXPRESSION;

    private Spel() {
        throw new IllegalAccessError("Spel is not support new instance for you!");
    }

    private static EvaluationContext wrapContext(Object context) {
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

    private static Expression expression(final String expression, final ParserContext context) {
        return PARSER.parseExpression(expression, context);
    }

}

