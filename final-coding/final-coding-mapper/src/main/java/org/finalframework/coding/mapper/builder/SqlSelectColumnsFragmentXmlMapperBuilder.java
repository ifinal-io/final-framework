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
import org.finalframework.data.annotation.ReadOnly;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:23:34
 * @since 1.0
 */
public class SqlSelectColumnsFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    public SqlSelectColumnsFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_SELECT_COLUMNS;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());

        final List<Element> elements = entity.getViews().stream().map(view -> whenViewSelectColumns(document, entity, view)).collect(Collectors.toList());
        elements.add(whenViewSelectColumns(document, entity, null));

        sql.appendChild(choose(document, elements));

        return sql;
    }

    private Element whenViewSelectColumns(@NonNull Document document, @NonNull Entity entity, @Nullable TypeElement view) {
        final List<String> columns = new ArrayList<>();
        entity.stream().filter(it -> !it.isTransient() && !it.isWriteOnly() && !it.isVirtual())
                .filter(it -> {
                    if (view == null) {
                        if (it.hasAnnotation(ReadOnly.class)) {
                            return false;
                        }
                        return true;
                    } else {
                        return it.hasView(view);
                    }
                })
                .forEach(property -> {
                    if (property.isReference()) {
                        final Entity multiEntity = property.toEntity();
                        List<String> referenceProperties = property.referenceProperties();
                        referenceProperties.stream()
                                .map(multiEntity::getProperty)
                                .filter(it -> !it.isTransient() && !it.isVirtual() && !it.isWriteOnly())
                                .forEach(multiProperty -> {
                                    columns.add(typeHandlers.formatPropertyReadColumn(entity, property, multiProperty));
                                });
                    } else {
                        columns.add(typeHandlers.formatPropertyReadColumn(entity, null, property));
                    }
                });

        final String test = view == null ? null : String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getQualifiedName().toString());

        return whenOrOtherwise(document, test, textNode(document, String.join(",", columns)));
    }
}
