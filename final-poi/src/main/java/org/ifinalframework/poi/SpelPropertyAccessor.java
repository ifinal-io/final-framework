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

package org.ifinalframework.poi;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * SpelPropertyAccessor.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SpelPropertyAccessor implements PropertyAccessor<Object, Object> {

    private final ExpressionParser expressionParser;

    public SpelPropertyAccessor() {
        this(new SpelExpressionParser(new SpelParserConfiguration(true, true)));
    }

    public SpelPropertyAccessor(final ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Nullable
    @Override
    public Object read(@NonNull final String expression, @Nullable final Object context) {

        if (context instanceof EvaluationContext) {
            return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION)
                    .getValue((EvaluationContext) context);
        }

        return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION).getValue(context);
    }

}
