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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:17:25
 * @since 1.0
 */
public class SelectCountMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {
    public SelectCountMethodXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "selectCount".equals(method.getSimpleName().toString());
    }


    @Override
    public Element buildMethodElement(ExecutableElement method, Document document, Entity entity) {
        final Element selectCount = document.createElement("select");
        selectCount.setAttribute("id", method.getSimpleName().toString());
        selectCount.setAttribute("resultType", Long.class.getCanonicalName());

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "SELECT COUNT(*) FROM");
        // <include refid="sql-table"/>
        trim.appendChild(include(document, SQL_TABLES));
        trim.appendChild(where(document,
                whenIdsNotNull(document, entity),
                whenQueryNotNull(document)
        ));
//        trim.appendChild(include(document, SQL_QUERY));
        selectCount.appendChild(trim);
        return selectCount;
    }
}
