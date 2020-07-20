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
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 14:27:05
 * @since 1.0
 */
public class BaseCriterion implements Serializable {

    protected void applyValueCriterion(@NonNull StringBuilder sql, Object value, String prefix, String suffix, String expression) {

        sql.append("<trim");

        if (prefix != null) {
            sql.append(" prefix=\"").append(prefix).append("\"");
        }

        if (suffix != null) {
            sql.append(" suffix=\"").append(suffix).append("\"");
        }

        sql.append(">");

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(sql, expression);
        } else if (value instanceof QProperty) {
            sql.append(((QProperty<?>) value).getColumn());
        } else if (value instanceof Iterable || value instanceof Array) {
            sql.append("<foreach collection=\"").append(expression).append("\" item=\"item\" separator=\",\">#{item}</foreach>");
        } else {
            sql.append("#{").append(expression).append("}");
        }

        sql.append("</trim>");

    }
}

