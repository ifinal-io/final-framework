/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.mybatis.sql.provider;

import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.core.IRecord;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.LastModified;
import org.ifinalframework.data.annotation.Metadata;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.data.mybatis.sql.AbsMapperSqlProvider;
import org.ifinalframework.data.mybatis.sql.ScriptMapperHelper;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.QProperty;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.velocity.Velocities;

import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author iimik
 * @version 1.0.0
 * @see AbsMapper#insert(String, Class, boolean, Collection)
 * @see AbsMapper#replace(String, Class, Collection)
 * @see AbsMapper#save(String, Class, Collection)
 * @since 1.0.0
 */
public class InsertSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String METHOD_INSERT = "insert";

    private static final String METHOD_REPLACE = "replace";

    private static final String METHOD_SAVE = "save";

    private static final String INSERT_INTO = "INSERT INTO";

    private static final String INSERT_IGNORE_INTO = "INSERT IGNORE INTO";

    private static final String REPLACE_INTO = "REPLACE INTO";

    private static final String DEFAULT_WRITER = "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end"
            + "#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    private static final String TRIM_END = "</trim>";

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see AbsMapper#insert(String, Class, boolean, Collection)
     */
    public String insert(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see AbsMapper#replace(String, Class, Collection)
     */
    public String replace(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see AbsMapper#save(String, Class, Collection)
     */
    public String save(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    public void doProvide(final StringBuilder sql, final ProviderContext context,
                          final Map<String, Object> parameters) {

        final String insertPrefix = getInsertPrefix(context.getMapperMethod(),
                parameters.containsKey("ignore") && Boolean.TRUE.equals(parameters.get("ignore")));

        final Class<?> view = parameters.containsKey("view") ? (Class<?>) parameters.get("view") : null;

        final Class<?> entityClazz = getEntityClass(context.getMapperType());


        final QEntity<?, ?> entity = DefaultQEntityFactory.INSTANCE.create(entityClazz);
        parameters.put("entity", entity);

        appendInsertOrReplaceOrSave(sql, insertPrefix);
        appendColumns(sql, entity);
        appendValues(sql, entity);
        if (METHOD_SAVE.equals(context.getMapperMethod().getName())) {
            appendOnDuplicateKeyUpdate(sql, entity, view);
        }

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void injectCreator(Class<?> entityClazz, Map<String, Object> parameters) {
        if (IRecord.class.isAssignableFrom(entityClazz)) {
            final IUser<?> user = UserContextHolder.getUser();
            if (Objects.nonNull(user)) {
                // set creator
                final Collection<IRecord> list = (Collection<IRecord>) parameters.get("list");
                for (IRecord item : list) {
                    item.setCreator(user);
                }
            }
        }
    }

    private String getInsertPrefix(final Method method, final boolean ignore) {

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
    private void appendInsertOrReplaceOrSave(final StringBuilder sql, final String insertPrefix) {

        sql.append("<trim prefix=\"").append(insertPrefix).append("\">")
                .append(ScriptMapperHelper.table())
                .append(TRIM_END);
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
     * @param sql    sql
     * @param entity entity
     */
    private void appendColumns(final StringBuilder sql, final QEntity<?, ?> entity) {

        // <trim prefix="(" suffix=)"" suffixOverrides=",">
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");

        // <if test="entity.property.hasView(view)">
        //      entity.property.column,
        // </if>
        entity.stream()
                .filter(QProperty::isWriteable)
                .forEach(property ->
                        sql.append("<if test=\"entity.getRequiredProperty('")
                                .append(property.getPath())
                                .append("').hasView(view)\">")
                                .append(property.getColumn())
                                .append(",</if>"));
        // </trim>
        sql.append(TRIM_END);
    }

    /**
     * <pre>
     *     <code>
     *         <foreach collection="list" item="entity" open="VALUES" separator=",">
     *              <trim prefix="(" suffix=")" suffixOverrides=",">
     *                  values
     *              </trim>
     *         </foreach>
     *     </code>
     * </pre>
     *
     * @param sql    sql
     * @param entity entity
     */
    private void appendValues(final StringBuilder sql, final QEntity<?, ?> entity) {

        sql.append("<foreach collection=\"list\" item=\"item\" open=\"VALUES\" separator=\",\">");

        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");

        entity.stream()
                .filter(QProperty::isWriteable)
                .forEach(property -> {
                    sql.append("<if test=\"entity.getRequiredProperty('")
                            .append(property.getPath())
                            .append("').hasView(view)\">");

                    if (property.getPath().contains(".")) {
                        String test = ScriptMapperHelper.formatTest("item", property.getPath(), false);
                        final String writer = Asserts.isBlank(property.getInsert()) ? DEFAULT_WRITER : property.getInsert();
                        sql.append(Velocities.eval(writer, buildPropertyMetadata(property, test)));

                    } else {
                        String test = ScriptMapperHelper.formatTest("item", property.getPath(), true);
                        final String writer = Asserts.isBlank(property.getInsert()) ? DEFAULT_WRITER : property.getInsert();
                        sql.append(Velocities.eval(writer, buildPropertyMetadata(property, test)));
                    }

                    sql.append(",").append("</if>");
                });

        sql.append(TRIM_END);

        sql.append("</foreach>");
    }

    private Metadata buildPropertyMetadata(QProperty<?> property, String test) {

        final Metadata metadata = new Metadata();
        metadata.setTest(test);
        metadata.setProperty(property.getName());
        metadata.setColumn(property.getColumn());
        metadata.setValue("item." + property.getPath());
        metadata.setJavaType(property.getType());
        metadata.setTypeHandler(property.getTypeHandler());
        return metadata;
    }

    /**
     * <trim prefix="ON DUPLICATE KEY UPDATE">
     * ${column} = value(${column}), version = version + 1, lastModified = NOW()
     * </trim>
     *
     * @param sql        sql
     * @param properties properties
     * @param view       view
     */
    private void appendOnDuplicateKeyUpdate(final StringBuilder sql, final QEntity<?, ?> properties,
                                            final Class<?> view) {

        final String onDuplicateKeyUpdate = properties.stream()
                .filter(property -> (property.isWriteable() && property.hasView(view))
                        || property.isVersionProperty()
                        || property.isAnnotationPresent(LastModified.class))
                .map(property -> {
                    String column = property.getColumn();
                    if (property.isVersionProperty()) {
                        return String.format("%s = %s + 1", column, column);
                    } else if (property.isAnnotationPresent(LastModified.class)) {
                        return String.format("%s = NOW()", column);
                    } else {
                        return String.format("%s = values(%s)", column, column);
                    }
                }).collect(Collectors.joining(","));

        sql.append("<trim prefix=\"ON DUPLICATE KEY UPDATE\">");

        sql.append(ScriptMapperHelper.cdata(onDuplicateKeyUpdate));

        sql.append(TRIM_END);
    }

}

