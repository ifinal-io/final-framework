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

import org.springframework.lang.NonNull;

import org.ifinalframework.core.IRecord;
import org.ifinalframework.data.annotation.Metadata;
import org.ifinalframework.data.mybatis.sql.AbsMapperSqlProvider;
import org.ifinalframework.data.mybatis.sql.ScriptMapperHelper;
import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.QProperty;
import org.ifinalframework.data.query.Update;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.velocity.Velocities;

import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;
import java.util.Objects;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class UpdateSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String DEFAULT_WRITER = String.join("", "<choose>",
            "   <when test=\"${selectiveTest}\">",
            "       ${column} = #{${value}#if($typeHandler)",
            "           #if($javaType), javaType=$!{javaType.canonicalName}#end",
            "           , typeHandler=$!{typeHandler.canonicalName}#end},",
            "   </when>",
            "   <when test=\"${test}\">",
            "       ${column} = #{${value}#if($typeHandler)",
            "           #if($javaType), javaType=$!{javaType.canonicalName}#end",
            "           , typeHandler=$!{typeHandler.canonicalName}#end},",
            "   </when>",
            "</choose>");

    private static final String PROPERTIES_PARAMETER_NAME = "properties";

    private static final String SELECTIVE_PARAMETER_NAME = "selective";

    private static final String ENTITY_PARAMETER_NAME = "entity";

    private static final String UPDATE_PARAMETER_NAME = "update";

    private static final String IDS_PARAMETER_NAME = "ids";

    private static final String QUERY_PARAMETER_NAME = "query";

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     */
    public String update(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doProvide(final StringBuilder sql, final ProviderContext context,
                          final Map<String, Object> parameters) {

        final Object query = parameters.get(QUERY_PARAMETER_NAME);

        Class<?> entity = getEntityClass(context.getMapperType());

        final QEntity<?, ?> properties = DefaultQEntityFactory.INSTANCE.create(entity);
        parameters.put(PROPERTIES_PARAMETER_NAME, properties);


        sql.append("<trim prefix=\"UPDATE\">").append("${table}").append("</trim>");

        sql.append("<set>");

        if (parameters.containsKey(UPDATE_PARAMETER_NAME) && parameters.get(UPDATE_PARAMETER_NAME) != null) {
            final Update updates = (Update) parameters.get(UPDATE_PARAMETER_NAME);
            for (int i = 0; i < updates.size(); i++) {
                Criterion criterion = updates.get(i);

                if (criterion instanceof CriterionAttributes) {
                    CriterionAttributes fragment = new CriterionAttributes();
                    fragment.putAll((CriterionAttributes) criterion);
                    fragment.setValue(String.format("update[%d]", i));
                    String value = Velocities.eval(fragment.getExpression(), fragment);
                    sql.append(value);
                } else {
                    throw new IllegalArgumentException("update not support criterion of " + criterion.getClass());
                }

            }

        } else {
            appendEntitySet(sql, properties, Boolean.TRUE.equals(parameters.get(SELECTIVE_PARAMETER_NAME)));
        }

        appendLastModifier(sql, entity, properties, parameters);
        appendVersionProperty(sql, properties);

        sql.append("</set>");

        if (parameters.containsKey(IDS_PARAMETER_NAME) && parameters.get(IDS_PARAMETER_NAME) != null) {
            sql.append(whereIdsNotNull());
        } else {
            appendQuery(sql, entity, query);
            appendOrders(sql);
            appendGroups(sql);
            appendLimit(sql);
        }

    }

    /**
     * @param entity     entity
     * @param properties properties
     * @param parameters parameters
     * @since 1.2.2
     */
    private void appendLastModifier(StringBuilder sql, Class<?> entity, QEntity<?, ?> properties, Map<String, Object> parameters) {
        if (IRecord.class.isAssignableFrom(entity)) {
            if (parameters.containsKey(UPDATE_PARAMETER_NAME) && Objects.nonNull(parameters.get(UPDATE_PARAMETER_NAME))) {
                sql.append("<if test=\"USER != null and USER.id != null\">")
                        .append(properties.getRequiredProperty("lastModifier.id").getColumn())
                        .append(" = #{USER.id},</if>");
                sql.append("<if test=\"USER != null and USER.name != null\">")
                        .append(properties.getRequiredProperty("lastModifier.name").getColumn())
                        .append(" = #{USER.name},</if>");
            }
        }

    }

    /**
     * @param sql    sql
     * @param entity entity
     */
    private void appendEntitySet(final @NonNull StringBuilder sql, final @NonNull QEntity<?, ?> entity, boolean selective) {

        entity.stream()
                .filter(QProperty::isModifiable)
                .forEach(property -> {
                    // <if test="properties.property.hasView(view)>"
                    sql.append("<if test=\"properties.getRequiredProperty('")
                            .append(property.getPath())
                            .append("').hasView(view)\">");

                    final String testWithSelective = ScriptMapperHelper.formatTest(ENTITY_PARAMETER_NAME, property.getPath(), true);
                    final String testNotWithSelective = ScriptMapperHelper.formatTest(ENTITY_PARAMETER_NAME, property.getPath(), false);


                    final String selectiveTest = testWithSelective == null
                            ? SELECTIVE_PARAMETER_NAME : "selective and " + testWithSelective;
                    final String test = testNotWithSelective == null ? "!selective" : "!selective and " + testNotWithSelective;

                    final Metadata metadata = new Metadata();
                    metadata.setTest(test);
                    metadata.setSelectiveTest(selectiveTest);
                    metadata.setProperty(property.getName());
                    metadata.setColumn(property.getColumn());
                    metadata.setValue("entity." + property.getPath());
                    metadata.setJavaType(property.getType());
                    if (Objects.nonNull(property.getTypeHandler())) {
                        metadata.setTypeHandler(property.getTypeHandler());
                    }

                    final String writer = Asserts.isBlank(property.getUpdate()) ? DEFAULT_WRITER : property.getUpdate();
                    final String value = Velocities.eval(writer, metadata);

                    sql.append(value);

                    sql.append("</if>");
                });
    }

    private void appendVersionProperty(final StringBuilder sql, final QEntity<?, ?> entity) {

        if (!entity.hasVersionProperty()) {
            return;
        }

        sql.append("<if test=\"properties.hasVersionProperty()\">");
        QProperty<Object> property = entity.getVersionProperty();
        final Metadata metadata = new Metadata();
        metadata.setProperty(property.getName());
        metadata.setColumn(property.getColumn());
        metadata.setValue("entity." + property.getPath());
        metadata.setJavaType(property.getType());
        if (Objects.nonNull(property.getTypeHandler())) {
            metadata.setTypeHandler(property.getTypeHandler());
        }
        String update = property.getUpdate();
        final String value = Velocities.eval(update, metadata);
        sql.append(value).append(",");
        sql.append("</if>");
    }


}

