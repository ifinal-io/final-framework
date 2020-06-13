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

import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.Array;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @since 1.0
 */
public interface SingleCriterion<T> extends SimpleCriterion {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T getValue();

    @Override
    default void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();
        final Object target = getTarget();
        final T value = getValue();

        final Element targetElement = document.createElement("trim");

        if (target instanceof CriterionTarget) {
            ((CriterionTarget) target).apply(targetElement, String.format("%s.target", expression));
        } else if (target instanceof QProperty) {
            targetElement.appendChild(document.createTextNode(((QProperty) target).getColumn()));
        }

        parent.appendChild(targetElement);

        final Element valueElement = document.createElement("trim");
        valueElement.setAttribute("prefix", " = ");

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(valueElement, String.format("%s.value", expression));
        } else if (value instanceof Iterable || value instanceof Array) {

        } else {
            valueElement.appendChild(document.createTextNode(String.format("#{%s.value}", expression)));
        }

        parent.appendChild(valueElement);

    }

    interface Builder<T> extends SimpleCriterion.Builder<SingleCriterion<T>, Builder<T>> {

        Builder<T> value(T value);
    }

}
