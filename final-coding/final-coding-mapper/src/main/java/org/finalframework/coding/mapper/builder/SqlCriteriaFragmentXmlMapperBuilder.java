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


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.annotation.query.AndOr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-28 10:59:26
 * @since 1.0
 */
public class SqlCriteriaFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    private static final Integer MAX_CRITERIA_LOOP = 3;

    public SqlCriteriaFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_CRITERIA;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        Element sql = sql(document, id());
        sql.appendChild(criteria(document, 1));
        return sql;
    }

    private Element criteria(Document document, int loop) {
        return choose(document, Arrays.asList(
                whenCriteriaAndOr(document, AndOr.AND, loop),
                whenCriteriaAndOr(document, AndOr.OR, loop)
        ));
    }

    private Element whenCriteriaAndOr(Document document, AndOr andOr, int loop) {

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criterion.criteria");
        foreach.setAttribute("item", "criterion");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", String.format(" %s ", andOr.name()));
        foreach.setAttribute("close", ")");
        foreach.appendChild(choose(document, Arrays.asList(
                whenCriteriaIsChain(document, loop),
                whenOrOtherwise(document, null, include(document, SQL_CRITERION))
        )));

        return whenOrOtherwise(document, "criterion.andOr.name == '" + andOr.name() + "'", foreach);
    }

    private Element whenCriteriaIsChain(Document document, int loop) {
        Element when = document.createElement("when");
        when.setAttribute("test", "criterion.chain");
        if (loop <= MAX_CRITERIA_LOOP) {
            when.appendChild(criteria(document, ++loop));
        }
        return when;
    }


}

