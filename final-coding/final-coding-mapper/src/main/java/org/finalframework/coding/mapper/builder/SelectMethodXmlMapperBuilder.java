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
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:01:11
 * @since 1.0
 */
public class SelectMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {

    private static final String METHOD_SELECT = "select";
    private static final String METHOD_SELECT_ONE = "selectOne";
    private static final Set<String> methods = new HashSet<>(Arrays.asList(METHOD_SELECT, METHOD_SELECT_ONE));

    public SelectMethodXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && methods.contains(method.getSimpleName().toString());
    }

    @Override
    public Element buildMethodElement(ExecutableElement method, Document document, Entity entity) {
        final Element sql = document.createElement("select");
        String methodName = method.getSimpleName().toString();
//        sql.setAttribute("resultMap", entity.getSimpleName() + "Map");
        sql.setAttribute("resultType", entity.getName());
        sql.setAttribute("id", methodName);
        Element select = document.createElement("trim");
        select.setAttribute("prefix", "SELECT");
        select.appendChild(include(document, SQL_SELECT_COLUMNS));
        sql.appendChild(select);

        Element from = document.createElement("trim");
        from.setAttribute("prefix", "FROM");
        from.appendChild(include(document, SQL_TABLES));
        sql.appendChild(from);
        sql.appendChild(where(document,
                METHOD_SELECT_ONE.equals(methodName) ? whenIdNotNull(document, entity) : whenIdsNotNull(document, entity),
                whenQueryNotNull(document)
        ));
        return sql;
    }

}
