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

package org.ifinalframework.data.query.sql;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import org.ifinalframework.core.Groupable;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.Limitable;
import org.ifinalframework.core.Orderable;
import org.ifinalframework.data.annotation.criterion.Criteria;
import org.ifinalframework.data.annotation.criterion.Criterion;
import org.ifinalframework.data.annotation.criterion.CriterionSqlProvider;
import org.ifinalframework.data.annotation.criterion.Or;
import org.ifinalframework.data.annotation.function.Function;
import org.ifinalframework.data.mapping.Entity;
import org.ifinalframework.data.query.AndOr;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.PageQuery;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.QEntityFactory;
import org.ifinalframework.data.query.criterion.CriterionHandlerRegistry;
import org.ifinalframework.data.query.criterion.VelocityCriterionValue;
import org.ifinalframework.data.util.TenantUtils;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.velocity.Velocities;

import org.apache.ibatis.type.TypeHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

/**
 * @author iimik
 * @version 1.0.0
 * @see Orderable
 * @see Groupable
 * @see Limitable
 * @since 1.0.0
 */
@Slf4j
public final class DefaultQueryProvider extends AbsQueryProvider {

    public static final String FORMAT = "%s.%s";

    private static final Set<String> IGNORE_ATTRIBUTES = Stream.of(
            CriterionAttributes.ATTRIBUTE_NAME_PROPERTY,
            CriterionAttributes.ATTRIBUTE_NAME_VALUE
    ).collect(Collectors.toSet());

    private final QEntityFactory entityFactory = DefaultQEntityFactory.INSTANCE;

    private final Object query;

    private final String where;


    public DefaultQueryProvider(final String expression, final Class<? extends IEntity> entity, final Object query) {

        this.query = query;

        final StringBuilder whereBuilder = new StringBuilder();
        whereBuilder.append("<where>");
        final QEntity<?, ?> properties = entityFactory.create(entity);

        if (TenantUtils.isTenant(entity)) {
            whereBuilder.append("<if test=\"properties.hasTenantProperty() and tenant != null\">")
                    .append("${properties.tenantProperty.column} = #{tenant} ")
                    .append("</if>");
        }

        if (query instanceof PageQuery pageQuery) {
            appendCriteria(whereBuilder, pageQuery.getCriteria(), AndOr.AND, "query.criteria");
        }

        final Class<?> queryClass = query.getClass();
        appendCriteria(whereBuilder, expression, properties, queryClass,
                AnnotatedElementUtils.isAnnotated(queryClass, Or.class) ? AndOr.OR : AndOr.AND);

        whereBuilder.append("</where>");

        this.where = whereBuilder.toString();

    }


    private void appendCriteria(StringBuilder sql, org.ifinalframework.data.query.Criteria criteria, AndOr andOr, String expression) {
        for (int i = 0; i < criteria.size(); i++) {
            org.ifinalframework.data.query.Criterion criterion = criteria.get(i);

            if (criterion instanceof CriterionAttributes) {
                CriterionAttributes attributes = ((CriterionAttributes) criterion);

                CriterionAttributes target = new CriterionAttributes();
                target.putAll(attributes);
                target.put("criterion", String.format("%s[%d]", expression, i));

                String column = target.getColumn();

                if (column.contains("${") || column.contains("#{")) {
                    column = Velocities.getValue(column, target);
                    target.setColumn(column);
                }

                target.put(CriterionAttributes.ATTRIBUTE_NAME_AND_OR, andOr);
                target.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format("%s[%d].value", expression, i));

                String value = new VelocityCriterionValue(
                        attributes.getString(CriterionAttributes.ATTRIBUTE_NAME_EXPRESSION))
                        .value(target);
                sql.append(value);
            } else if (criterion instanceof org.ifinalframework.data.query.Criteria) {

                sql.append("<trim prefix=\"").append(andOr).append(" (\" suffix=\")\" prefixOverrides=\"AND |OR\">");

                org.ifinalframework.data.query.Criteria loopCriteria = (org.ifinalframework.data.query.Criteria) criterion;
                appendCriteria(sql, loopCriteria, loopCriteria.getAndOr(), String.format("%s[%d]", expression, i));

                sql.append("</trim>");

            }

        }
    }

    private void appendCriteria(final StringBuilder sql, final String expression, final QEntity<?, ?> entity,
                                final Class<?> query, final AndOr andOr) {

        Entity.from(query)
                .forEach(property -> {
                    if (property.isAnnotationPresent(Criterion.class)) {
                        final Criterion criterion = property.getRequiredAnnotation(Criterion.class);
                        Class<? extends Annotation> annotation = criterion.annotation();
                        Field field = property.getField();
                        Objects.requireNonNull(field, "property filed can not be null:" + property.getName());


                        CriterionSqlProvider criterionSqlProvider = CriterionHandlerRegistry.getInstance()
                                .get(CriterionSqlProvider.class);
                        AnnotationAttributes criterionAttributes = null;
                        if (Criterion.class == annotation) {
                            criterionAttributes = new AnnotationAttributes();
                        } else {
                            criterionAttributes = AnnotatedElementUtils
                                    .getMergedAnnotationAttributes(field, annotation);

                            Objects.requireNonNull(criterionAttributes,
                                    "not found annotation of @" + annotation.getSimpleName() + " at " + query.getSimpleName() + "."
                                            + property.getName());
                        }


                        final CriterionAttributes metadata = new CriterionAttributes();

                        final String path = Asserts.nonBlank(criterion.property())
                                ? criterion.property()
                                : property.getName();

                        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_AND_OR, andOr);
                        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_QUERY, expression);
                        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY,
                                String.format(FORMAT, expression, property.getName()));
                        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_COLUMN, entity.getRequiredProperty(path).getColumn());


                        // process @Function annotation
                        if (property.isAnnotationPresent(Function.class)) {
                            final Function function = property.findAnnotation(Function.class);
                            final Class<? extends Annotation> functionAnnotation = function.annotation();

                            AnnotationAttributes functionAttributes;
                            if (Function.class != functionAnnotation) {
                                functionAttributes = AnnotatedElementUtils
                                        .getMergedAnnotationAttributes(property.getField(), function.annotation());
                                Objects.requireNonNull(functionAttributes);

                                for (Map.Entry<String, Object> entry : functionAttributes.entrySet()) {
                                    metadata.put(entry.getKey(), entry.getValue());
                                }

                                appendAnnotationAttributesToMetadata(functionAttributes, metadata);
                            } else {
                                functionAttributes = new AnnotationAttributes();
                            }
                            functionAttributes.put("expression", function.value());
                            criterionSqlProvider.function(functionAttributes, metadata);


                        }

                        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format(FORMAT, expression, property.getName()));

                        appendAnnotationAttributesToMetadata(criterionAttributes, metadata);
                        criterionAttributes.put("expression", criterion.value());
                        // process @Criterion annotation
                        final String value = criterionSqlProvider.provide(criterionAttributes, metadata);
                        sql.append(value);
                    } else if (property.isAnnotationPresent(Criteria.class)) {
                        sql.append("<if test=\"").append(expression).append(".").append(property.getName())
                                .append(" != null\">");
                        sql.append("<trim prefix=\" ").append(andOr.name())
                                .append(" (\" suffix=\")\" prefixOverrides=\"AND |OR \">");
                        appendCriteria(sql, expression + "." + property.getName(), entity, property.getType(),
                                property.isAnnotationPresent(Or.class) ? AndOr.OR : AndOr.AND);
                        sql.append("</trim>");
                        sql.append("</if>");
                    }
                });
    }


    private void appendAnnotationAttributesToMetadata(final AnnotationAttributes annotationAttributes,
                                                      final CriterionAttributes metadata) {
        //append annotation attributes
        for (Map.Entry<String, Object> entry : annotationAttributes.entrySet()) {
            if (IGNORE_ATTRIBUTES.contains(entry.getKey())
                    || (CriterionAttributes.ATTRIBUTE_NAME_JAVA_TYPE.equals(entry.getKey()) && Object.class
                    .equals(entry.getValue()))
                    || (CriterionAttributes.ATTRIBUTE_NAME_TYPE_HANDLER.equals(entry.getKey()) && TypeHandler.class
                    .equals(entry.getValue()))) {
                continue;
            }
            metadata.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String where() {
        return this.where;
    }


}

