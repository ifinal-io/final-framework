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

package org.finalframework.data.query.criterion.function;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.operation.Operation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 13:22:51
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class SimpleCriterionFunction implements CriterionFunction {
    private final Object value;
    private final Operation operation;

    @Override
    public void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();

        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", name() + "(");
        trim.setAttribute("suffix", ")");

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(trim, String.format("%s.value", expression));
        } else if (value instanceof QProperty) {
            trim.appendChild(document.createCDATASection(String.format("${%s.value.column}", expression)));
        } else {
            trim.appendChild(document.createCDATASection(String.format("#{%s.value}", expression)));
        }

        parent.appendChild(trim);


    }
}

