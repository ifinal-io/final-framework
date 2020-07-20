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


import lombok.Getter;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-06 20:33:13
 * @since 1.0
 */
@Getter
public class AbsCompareCriterionOperation extends BaseCriterion implements CompareCriterionOperation {

    private final Object target;
    private final CompareOperation operation;
    private final Object value;
    private final Object min;
    private final Object max;

    private AbsCompareCriterionOperation(AbsCompareCriterionOperationBuilder builder) {
        this.target = builder.target;
        this.operation = builder.operation;
        this.value = builder.value;
        this.min = builder.min;
        this.max = builder.max;
    }

    static CompareCriterionOperationBuilder builder() {
        return new AbsCompareCriterionOperationBuilder();
    }

    @Override
    public void apply(StringBuilder parent, String expression) {
        switch (operation) {
            case NULL:
                applyValueCriterion(parent, target, null, "IS NULL", expression + ".target");
                break;
            case NOT_NULL:
                applyValueCriterion(parent, target, null, "IS NOT NULL", expression + ".target");
                break;
            case EQUAL:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " = ", null, expression + ".value");
                break;
            case NOT_EQUAL:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " != ", null, expression + ".value");
                break;
            case GREAT_THAN:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " > ", null, expression + ".value");
                break;
            case GREAT_THAN_EQUAL:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " >= ", null, expression + ".value");
                break;
            case LESS_THAN:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " < ", null, expression + ".value");
                break;
            case LESS_THAN_EQUAL:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " <= ", null, expression + ".value");
                break;
            case LIKE:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " LIKE ", null, expression + ".value");
                break;
            case NOT_LIKE:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " NOT LIKE ", null, expression + ".value");
                break;
            case IN:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " IN (", ")", expression + ".value");
                break;
            case NOT_IN:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " NOT IN (", ")", expression + ".value");
                break;
            case BETWEEN:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " BETWEEN ", null, expression + ".min");
                applyValueCriterion(parent, value, " AND ", null, expression + ".max");
                break;
            case NOT_BETWEEN:
                applyValueCriterion(parent, target, null, null, expression + ".target");
                applyValueCriterion(parent, value, " NOT BETWEEN ", null, expression + ".min");
                applyValueCriterion(parent, value, " AND ", null, expression + ".max");
                break;

        }


    }



    private static class AbsCompareCriterionOperationBuilder implements CompareCriterionOperation.CompareCriterionOperationBuilder {
        private Object target;
        private CompareOperation operation;
        private Object value;
        private Object min;
        private Object max;

        @Override
        public CompareCriterionOperationBuilder target(Object target) {
            this.target = target;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder operation(CompareOperation operation) {
            this.operation = operation;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder value(Object value) {
            this.value = value;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder min(Object min) {
            this.min = min;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder max(Object max) {
            this.max = max;
            return this;
        }

        @Override
        public CompareCriterionOperation build() {
            return new AbsCompareCriterionOperation(this);
        }
    }

}

