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

package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.Metadata;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.util.Velocities;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#select(String, Class, Collection, Query)
 * @since 1.0
 */
public class SelectSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String SELECT_METHOD_NAME = "select";
    private static final String SELECT_ONE_METHOD_NAME = "selectOne";
    private static final String DEFAULT_READER = "${column}";


    public String select(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    public String selectOne(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {

        Object id = parameters.get("id");
        Object query = parameters.get("query");

        Class<?> view = (Class<?>) parameters.get("view");

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        final Element select = helper.trim().prefix("SELECT").build();


        select.appendChild(helper.cdata(
                properties.stream()
                        .filter(property -> property.isReadable() && property.hasView(view))
                        .map(property -> {

                            final Metadata metadata = new Metadata();

                            metadata.setProperty(property.getName());
                            metadata.setColumn(property.getColumn());
                            metadata.setValue(property.getName());
                            metadata.setJavaType(property.getType());
                            metadata.setTypeHandler(property.getTypeHandler());

                            final String reader = Assert.isBlank(property.getReader()) ? DEFAULT_READER : property.getReader();

                            final String value = Velocities.getValue(reader, metadata);

                            return value;
//
                        }).collect(Collectors.joining(","))
        ));


//        final Element foreach = helper.foreach().collection("properties").item("property").separator(",").build();
//
//        final Element ifHasView = document.createElement("if");
//        ifHasView.setAttribute("test", "property.hasView(view)");
//        ifHasView.appendChild(helper.cdata("${property.column}"));
//        foreach.appendChild(ifHasView);
//
////        foreach.appendChild();
//        select.appendChild(foreach);

        script.appendChild(select);
        final Node from = helper.trim().prefix("FROM").build();
        from.appendChild(helper.cdata("${table}"));
        script.appendChild(from);


        if (SELECT_ONE_METHOD_NAME.equals(context.getMapperMethod().getName()) && parameters.get("id") != null) {
            script.appendChild(where(document, whereIdNotNull(document)));
        } else if (SELECT_METHOD_NAME.equals(context.getMapperMethod().getName()) && parameters.get("ids") != null) {
            script.appendChild(where(document, whereIdsNotNull(document)));
        } else if (query instanceof Query) {
            ((Query) query).apply(script, "query");
        }

    }
}

