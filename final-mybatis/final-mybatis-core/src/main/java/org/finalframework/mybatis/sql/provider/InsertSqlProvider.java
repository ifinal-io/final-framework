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


import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.LastModified;
import org.finalframework.data.annotation.Version;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <pre>
 *     <code>
 *         <script>
 *             <trim prefix="INSERT INFO | INSERT IGNORE INTO | REPLACE INTO">
 *                 ${table}
 *             </trim>
 *             <trim prefix="(" suffix=")">
 *                  columns
 *             </trim>
 *             <foreach collection="list" item="entity" open="VALUES">
 *                  <trim prefix="(" suffix=")">
 *                      values
 *                  </trim>
 *             </foreach>
 *             <trim prefix="ON DUPLICATE KEY UPDATE">
 *                  column = values(column),
 *                  version = version + 1,
 *                  last_modified = NOW()
 *             </trim>
 *         </script>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
 * @see org.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
 * @see org.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
 * @since 1.0
 */
public class InsertSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    public static final String METHOD_INSERT = "insert";
    public static final String METHOD_REPLACE = "replace";
    public static final String METHOD_SAVE = "save";

    private static final String INSERT_INTO = "INSERT INTO";
    private static final String INSERT_IGNORE_INTO = "INSERT IGNORE INTO";
    private static final String REPLACE_INTO = "REPLACE INTO";
    private static final String VALUES = "VALUES";
    public static final String ON_DUPLICATE_KEY_UPDATE = "ON DUPLICATE KEY UPDATE";


    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
     */
    public String insert(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
     */
    public String replace(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
     */
    public String save(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {
        final String insertPrefix = getInsertPrefix(context.getMapperMethod(),
                parameters.containsKey("ignore") && Boolean.TRUE.equals(parameters.get("ignore")));

        Class<?> view = (Class<?>) parameters.get("view");

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        /*
         *  <trim prefix="INSERT INFO | INSERT IGNORE INTO | REPLACE INTO">
         *      ${table}
         *  </trim>
         */
        final Element insert = helper.trim().prefix(insertPrefix).build();
        insert.appendChild(helper.cdata("${table}"));
        script.appendChild(insert);

        /*
         * <trim prefix="(" suffix=")">
         *      columns
         * </trim>
         */
        final Element columns = helper.trim().prefix("(").suffix(")").build();
        columns.appendChild(helper.cdata(
                properties.stream()
                        .filter(property -> property.isWriteable() && property.hasView(view))
                        .map(QProperty::getColumn)
                        .collect(Collectors.joining(","))
        ));
        script.appendChild(columns);

        final Element foreach = helper.foreach().collection("list").item("entity").open(VALUES).build();

        properties.stream()
                .filter(property -> property.isWriteable() && property.hasView(view))
                .forEach(property -> {
                    foreach
                            .appendChild(helper.bind(property.getName(), helper.formatBindValue("entity", property.getPath())));
                });

        final Element values = helper.trim().prefix("(").suffix(")").build();

        values.appendChild(helper.cdata(
                properties.stream()
                        .filter(property -> property.isWriteable() && property.hasView(view))
                        .map(property -> {
                            final StringBuilder builder = new StringBuilder();

                            builder.append("#{").append(property.getName());
                            // javaType
                            builder.append(",javaType=").append(property.getType().getCanonicalName());
                            // typeHandler
                            final Class<? extends TypeHandler> typeHandler = getTypeHandler(property);
                            if (typeHandler != null) {
                                builder.append(",typeHandler=").append(typeHandler.getCanonicalName());
                            }
                            builder.append("}");

                            return builder.toString();
                        })
                        .collect(Collectors.joining(","))));

        foreach.appendChild(values);

        script.appendChild(foreach);

        if (METHOD_SAVE.equals(context.getMapperMethod().getName())) {
            Element onDuplicateKeyUpdate = helper.trim().prefix(ON_DUPLICATE_KEY_UPDATE).build();

            onDuplicateKeyUpdate.appendChild(helper.cdata(
                    properties.stream()
                            .filter(property -> (property.isWriteable() && property.hasView(view))
                                    || property.getProperty().hasAnnotation(Version.class)
                                    || property.getProperty().hasAnnotation(LastModified.class))
                            .map(property -> {
                                String column = property.getColumn();
                                if (property.getProperty().hasAnnotation(Version.class)) {
                                    return String.format("%s = %s + 1", column, column);
                                } else if (property.getProperty().hasAnnotation(LastModified.class)) {
                                    return String.format("%s = NOW()", column);
                                } else {
                                    return String.format("%s = values(%s)", column, column);
                                }
                            })
                            .collect(Collectors.joining(","))
            ));

            script.appendChild(onDuplicateKeyUpdate);
        }

    }

    private boolean filterInsertProperties(QProperty property, Class<?> view) {
        return property.isWriteable() && property.hasView(view);
    }


    private String getInsertPrefix(Method method, boolean ignore) {
        switch (method.getName()) {
            case METHOD_INSERT:
                return ignore ? INSERT_IGNORE_INTO : INSERT_INTO;
            case METHOD_REPLACE:
                return REPLACE_INTO;
            case METHOD_SAVE:
                return INSERT_INTO;
        }
        throw new IllegalArgumentException();
    }


}

