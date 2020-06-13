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
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.StringOperation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 14:20:33
 * @since 1.0
 */
public class SqlCriterionFunctionFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    private final Integer loop;

    public SqlCriterionFunctionFragmentXmlMapperBuilder(TypeHandlers typeHandlers, Integer loop) {
        super(typeHandlers);
        this.loop = loop;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = sql(document, id());
        sql.appendChild(choose(document, Arrays.asList(
                whenOrOtherwise(document, String.format("'%s' == ${function}.name()", "NOW"), cdata(document, "NOW()")),
                whenOrOtherwise(document, String.format("'%s' == ${function}.name()", DateOperation.DATE), date(document)),
                whenOrOtherwise(document, String.format("'%s' == ${function}.name()", StringOperation.CONCAT),
                        concat(document))
        )));
        return sql;
    }

    private Element date(Document document) {
        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "DATE(");
        trim.setAttribute("suffix", ")");
        trim.appendChild(include(document, getValueRefId(), property(document, "value", "${function}.value")));
        return trim;
    }

    private Element concat(Document document) {
        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "CONCAT(");
        trim.setAttribute("suffix", ")");
        trim.appendChild(include(document, getValueRefId(), property(document, "value", "${function}.value")));
        return trim;
    }

    private String getValueRefId() {
        return SQLConstants.SQL_CRITERION_VALUE + "-" + (loop + 1);
    }

    @Override
    public String id() {
        return SQLConstants.SQL_CRITERION_FUNCTION + (loop == 0 ? "" : "-" + loop);
    }
}

