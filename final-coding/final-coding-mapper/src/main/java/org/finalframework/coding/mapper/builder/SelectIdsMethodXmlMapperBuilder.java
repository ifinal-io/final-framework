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

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:15:36
 * @since 1.0
 */
public class SelectIdsMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {
    public SelectIdsMethodXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "selectIds".equals(method.getSimpleName().toString());
    }

    @Override
    public Element buildMethodElement(ExecutableElement method, Document document, Entity entity) {
        final Element select = document.createElement("select");
        select.setAttribute("id", method.getSimpleName().toString());
        final Property idProperty = entity.getRequiredIdProperty();
        select.setAttribute("resultType", idProperty.getJavaTypeElement().getQualifiedName().toString());

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", String.format("SELECT %s FROM", idProperty.getColumn()));

        trim.appendChild(include(document, SQL_TABLES));
        trim.appendChild(include(document, SQL_QUERY));
        select.appendChild(trim);
        return select;
    }
}
