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

package org.finalframework.coding.mapper.builder;


import java.util.Arrays;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.SQLConstants;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 14:31:37
 * @since 1.0
 */
public class SqlCriterionValueFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    private static final Integer MAX_LOOP = 3;
    private final Integer loop;

    public SqlCriterionValueFragmentXmlMapperBuilder(TypeHandlers typeHandlers, Integer loop) {
        super(typeHandlers);
        this.loop = loop;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = sql(document, id());
        final Element whenProperty = document.createElement("when");
        whenProperty
                .setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isProperty(${value})");
        whenProperty.appendChild(cdata(document, "\\${${value}.column}"));

        final Element whenCollection = document.createElement("when");
        whenCollection
                .setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isCollection(${value})");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "${value}");
        foreach.setAttribute("item", "item");
        foreach.setAttribute("separator", ",");
        foreach.appendChild(textNode(document, "#{item}"));
        whenCollection.appendChild(foreach);

        final Element whenValue = document.createElement("when");
        whenValue.setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isValue(${value})");
        if (loop < MAX_LOOP) {
            whenValue.appendChild(cdata(document, "${value}.value"));
//            whenValue.appendChild(include(document, SQLConstants.SQL_CRITERION_VALUE +  "-" + (loop + 1),
//                property(document, "value", "${value}.value")));

        }
        final Element whenFunction = document.createElement("when");
        whenFunction
                .setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isFunction(${value})");
        if (loop < MAX_LOOP) {
            whenFunction.appendChild(
                    include(document, SQLConstants.SQL_CRITERION_FUNCTION + (loop == 0 ? "" : "-" + loop),
                            property(document, "function", "${value}")));
        }
        sql.appendChild(choose(document, Arrays.asList(
                whenProperty, whenFunction, whenCollection, whenValue,
                whenOrOtherwise(document, null, cdata(document, "#{${value}}"))
        )));

        return sql;
    }


    @Override
    public String id() {
        return SQLConstants.SQL_CRITERION_VALUE + (loop == 0 ? "" : "-" + loop);
    }
}

