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
import org.finalframework.data.annotation.LastModified;
import org.finalframework.data.annotation.Metadata;
import org.finalframework.data.annotation.Version;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.util.Velocities;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

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
 *             <foreach collection="list" item="entity" open="VALUES" separator=",">
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
@SuppressWarnings("unused")
public class InsertSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    public static final String METHOD_INSERT = "insert";
    public static final String METHOD_REPLACE = "replace";
    public static final String METHOD_SAVE = "save";

    private static final String INSERT_INTO = "INSERT INTO";
    private static final String INSERT_IGNORE_INTO = "INSERT IGNORE INTO";
    private static final String REPLACE_INTO = "REPLACE INTO";
    private static final String VALUES = "VALUES";
    public static final String ON_DUPLICATE_KEY_UPDATE = "ON DUPLICATE KEY UPDATE";

    private static final String DEFAULT_WRITER = "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";


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
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        final String insertPrefix = getInsertPrefix(context.getMapperMethod(),
                parameters.containsKey("ignore") && Boolean.TRUE.equals(parameters.get("ignore")));

        Class<?> view = (Class<?>) parameters.get("view");

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);


        appendInsertOrReplaceOrSave(sql, insertPrefix);
        appendColumns(sql, properties, view);
        appendValues(sql, properties, view);
        if (METHOD_SAVE.equals(context.getMapperMethod().getName())) {
            appendOnDuplicateKeyUpdate(sql, properties, view);
        }

    }


    private String getInsertPrefix(Method method, boolean ignore) {
        switch (method.getName()) {
            case METHOD_INSERT:
                return ignore ? INSERT_IGNORE_INTO : INSERT_INTO;
            case METHOD_REPLACE:
                return REPLACE_INTO;
            case METHOD_SAVE:
                return INSERT_INTO;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * <trim prefix="INSERT INTO | INSERT IGNORE INTO | REPLACE INTO">
     * ${table}
     * </trim>
     */
    private void appendInsertOrReplaceOrSave(StringBuilder sql, String insertPrefix) {
        sql.append("<trim prefix=\"").append(insertPrefix).append("\">")
                .append(ScriptMapperHelper.table())
                .append("</trim>");
    }

    /**
     * <pre>
     *     <code>
     *         <trim prefix="(" suffix=")">
     *              ${columns}
     *         </trim>
     *     </code>
     * </pre>
     *
     * @param sql        sql
     * @param properties properties
     * @param view       view
     */
    private void appendColumns(StringBuilder sql, QEntity<?, ?> properties, Class<?> view) {

        sql.append("<trim prefix=\"(\" suffix=\")\">");
        sql.append(ScriptMapperHelper.cdata(
                properties.stream()
                        .filter(property -> property.isWriteable() && property.hasView(view))
                        .map(QProperty::getColumn)
                        .collect(Collectors.joining(","))
        ));
        sql.append("</trim>");
    }

    /**
     * <pre>
     *     <code>
     *         <foreach collection="list" item="entity" open="VALUES" separator=",">
     *              <trim prefix="(" suffix=")">
     *                  values
     *              </trim>
     *         </foreach>
     *     </code>
     * </pre>
     *
     * @param sql        sql
     * @param properties properties
     * @param view       view
     */
    private void appendValues(StringBuilder sql, QEntity<?, ?> properties, Class<?> view) {
        sql.append("<foreach collection=\"list\" item=\"entity\" open=\"VALUES\" separator=\",\">");

        properties.stream()
                .filter(property -> property.isWriteable() && property.hasView(view))
                .forEach(property -> sql.append(ScriptMapperHelper.bind(property.getName(), ScriptMapperHelper.formatBindValue("entity", property.getPath()))));

        sql.append("<trim prefix=\"(\" suffix=\")\">");
        final String values = properties.stream()
                .filter(property -> property.isWriteable() && property.hasView(view))
                .map(property -> {
                    final Metadata metadata = new Metadata();
                    metadata.setProperty(property.getName());
                    metadata.setColumn(property.getColumn());
                    metadata.setValue(property.getName());
                    metadata.setJavaType(property.getType());
                    metadata.setTypeHandler(property.getTypeHandler());

                    final String writer = Assert.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                    return Velocities.getValue(writer, metadata);
                })
                .collect(Collectors.joining("\n,"));

        sql.append(values);

        sql.append("</trim>");

        sql.append("</foreach>");
    }


    /**
     * <trim prefix="ON DUPLICATE KEY UPDATE">
     * ${column} = value(${column}),
     * version = version + 1,
     * lastModified = NOW()
     * </trim>
     *
     * @param sql        sql
     * @param properties properties
     * @param view       view
     */
    private void appendOnDuplicateKeyUpdate(StringBuilder sql, QEntity<?, ?> properties, Class<?> view) {
        final String onDuplicateKeyUpdate = properties.stream()
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
                }).collect(Collectors.joining(","));

        sql.append("<trim prefix=\"ON DUPLICATE KEY UPDATE\">");

        sql.append(ScriptMapperHelper.cdata(onDuplicateKeyUpdate));

        sql.append("</trim>");
    }


}

