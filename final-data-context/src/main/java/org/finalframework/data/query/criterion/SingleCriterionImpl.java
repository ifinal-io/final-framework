/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.query.criterion;


import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.Operation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.finalframework.data.query.operation.Operation.*;
import static org.finalframework.data.query.operation.Operation.CompareOperation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class SingleCriterionImpl<T> extends SimpleCriterionImpl<T> implements SingleCriterion<T> {

    private static final Set<Operation> OPERATOR_IN = new HashSet<>(Arrays.asList(
            IN,
            NOT_IN
    ));

    private final T value;

    private SingleCriterionImpl(BuilderImpl<T> builder) {
        super(builder);
        this.value = builder.value;
    }

    public static <T> SingleCriterion.Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public T getValue() {
        return value;
    }

    public String getCriterionValue() {
        String expression = OPERATOR_IN.contains(getOperation()) ? "value" : "criterion.value";
        return ((CriterionValueImpl) CriterionValue.from(value)).getSqlExpression(expression);
    }

    @Override
    public String toString() {
        final Operation operation = getOperation();
        if (operation instanceof CompareOperation) {
            CompareOperation value = (CompareOperation) operation;
            switch (value) {
                case EQUAL:
                    return String.format(" %s = %s", getCriterionTarget(), CriterionValue.from(getValue()));
                case NOT_BETWEEN:
                    return String.format(" %s != %s", getCriterionTarget(), CriterionValue.from(getValue()));
            }
        } else if (operation instanceof JsonOperation) {
            JsonOperation value = (JsonOperation) operation;
            switch (value) {
                case JSON_CONTAINS:
                    return String.format("JSON_CONTAINS(%s,)", getCriterionTarget(), CriterionValue.from(getValue()));
            }
        }

        return "";

    }

    private static class BuilderImpl<T> extends AbsBuilder<SingleCriterion<T>, SingleCriterion.Builder<T>>
            implements SingleCriterion.Builder<T> {
        private T value;

        @Override
        public SingleCriterion.Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        @Override
        public SingleCriterion<T> build() {
            return new SingleCriterionImpl<>(this);
        }
    }
}
