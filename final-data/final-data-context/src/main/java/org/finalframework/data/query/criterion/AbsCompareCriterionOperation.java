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
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.SqlNode;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.Array;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-06 20:33:13
 * @since 1.0
 */
@Getter
public class AbsCompareCriterionOperation implements CompareCriterionOperation {

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
    public void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();
        switch (operation) {
            case NULL:
                applyValueCriterion(document, parent, target, null, "IS NULL", expression + ".target");
                break;
            case NOT_NULL:
                applyValueCriterion(document, parent, target, null, "IS NOT NULL", expression + ".target");
                break;
            case EQUAL:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " = ", null, expression + ".value");
                break;
            case NOT_EQUAL:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " != ", null, expression + ".value");
                break;
            case GREAT_THAN:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " > ", null, expression + ".value");
                break;
            case GREAT_THAN_EQUAL:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " >= ", null, expression + ".value");
                break;
            case LESS_THAN:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " < ", null, expression + ".value");
                break;
            case LESS_THAN_EQUAL:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " <= ", null, expression + ".value");
                break;
            case LIKE:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " LIKE ", null, expression + ".value");
                break;
            case NOT_LIKE:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " NOT LIKE ", null, expression + ".value");
                break;
            case IN:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " IN (", ")", expression + ".value");
                break;
            case NOT_IN:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " NOT IN (", ")", expression + ".value");
                break;
            case BETWEEN:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " BETWEEN ", null, expression + ".min");
                applyValueCriterion(document, parent, value, " AND ", null, expression + ".max");
                break;
            case NOT_BETWEEN:
                applyValueCriterion(document, parent, target, null, null, expression + ".target");
                applyValueCriterion(document, parent, value, " NOT BETWEEN ", null, expression + ".min");
                applyValueCriterion(document, parent, value, " AND ", null, expression + ".max");
                break;

        }


    }

    private void applyValueCriterion(@NonNull Document document, @NonNull Node parent, Object value, String prefix, String suffix, String expression) {
        final Element trim = document.createElement("trim");
        if (prefix != null) {
            trim.setAttribute("prefix", prefix);
        }
        if (suffix != null) {
            trim.setAttribute("suffix", suffix);
        }


        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(trim, expression);
        } else if (value instanceof QProperty) {
//            trim.appendChild(document.createTextNode(((QProperty) target).getColumn()));
            trim.appendChild(document.createTextNode(String.format("${%s.column}", expression)));
        } else if (value instanceof Iterable || value instanceof Array) {

        } else {
            trim.appendChild(document.createTextNode(String.format("#{%s}", expression)));
        }

        parent.appendChild(trim);
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

