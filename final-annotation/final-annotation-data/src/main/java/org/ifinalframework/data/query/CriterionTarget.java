/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.query;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * CriterionTarget.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class CriterionTarget implements FunctionCriteriable<Object> {

    private String column;

    private final CriterionAttributes criterion;

    private CriterionTarget(final String column) {
        this.column = Objects.requireNonNull(column, "criterion column can not be null.");
        this.criterion = new CriterionAttributes();
        this.criterion.put("andOr", AndOr.AND);
    }

    /**
     * build an instance from column
     * @param column column
     */
    public static CriterionTarget from(String column) {
        return new CriterionTarget(column);
    }

    @NonNull
    @Override
    public Criterion condition(@NonNull final String expression, @Nullable final Object value,
        @Nullable final Consumer<CriterionAttributes> consumer) {

        criterion.setExpression(expression);
        criterion.setColumn(column);
        criterion.setValue(value);


        if (Objects.nonNull(consumer)) {
            consumer.accept(criterion);
        }

        return criterion;
    }

    @Override
    public Criteriable<Object> apply(@NonNull final UnaryOperator<String> column,
        @Nullable final Consumer<CriterionAttributes> consumer) {

        this.column = column.apply(this.column);

        if (Objects.nonNull(consumer)) {
            consumer.accept(criterion);
        }

        return this;
    }

}
