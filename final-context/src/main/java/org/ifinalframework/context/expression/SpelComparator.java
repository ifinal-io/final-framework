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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.3.0
 **/
@Slf4j
@RequiredArgsConstructor
public class SpelComparator {


    private final Comparator comparator = new NotEqualsAndExceptionComparator();


    public List<ExpressionItem> compare(@NonNull Object leftObj, @NonNull Object rightObj, @NonNull Collection<String> expressions) {
        final List<ExpressionItem> result = new ArrayList<>(expressions.size());
        EvaluationContext leftContext = Spel.wrapContext(leftObj);
        EvaluationContext rightContext = Spel.wrapContext(rightObj);

        for (String expression : expressions) {

            Object leftValue = null;
            Object rightValue = null;
            Exception exception = null;

            boolean compare = true;

            try {
                Expression express = Spel.expression(expression, ParserContext.TEMPLATE_EXPRESSION);
                leftValue = express.getValue(leftContext);
                rightValue = express.getValue(rightContext);

            } catch (Exception e) {
                logger.warn("parse exception on expression={}", expression, e);
                exception = e;
            } finally {
                result.add(new ExpressionItem(expression, leftValue, rightValue, exception));
                compare = comparator.compare(expression, leftValue, rightValue, exception);
            }


            if (!compare) {
                break;
            }

        }

        return result;
    }


}
