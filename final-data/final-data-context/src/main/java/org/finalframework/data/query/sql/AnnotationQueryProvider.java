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

package org.finalframework.data.query.sql;


import lombok.extern.slf4j.Slf4j;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.query.*;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.criterion.CriterionHandlerRegistry;
import org.finalframework.data.query.criterion.FunctionHandlerRegistry;
import org.finalframework.data.util.Velocities;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 18:05:29
 * @since 1.0
 */
@Slf4j
public class AnnotationQueryProvider implements QueryProvider {

    public static final AnnotationQueryProvider INSTANCE = new AnnotationQueryProvider();

    private AnnotationQueryProvider() {
    }

    @Override
    public String provide(String expression, Class<?> entity, Class<?> query) {

        final StringBuilder builder = new StringBuilder();

        builder.append("<where>");

        String offset = null;
        String limit = null;

        final QEntity<?, ?> properties = QEntity.from(entity);
        final Criteria criteria = AnnotationUtils.findAnnotation(query, Criteria.class);
        AndOr andOr = criteria != null ? criteria.value() : AndOr.AND;
        final Entity<?> queryEntity = Entity.from(query);
        for (Property property : queryEntity) {

            if (property.isAnnotationPresent(Criterion.class)) {
                final Criterion criterion = property.getRequiredAnnotation(Criterion.class);
                final String path = Assert.isBlank(criterion.property()) ? property.getName() : criterion.property();
                // format the column with function

                final Metadata metadata = Metadata.builder().andOr(andOr)
                        .query(expression)
                        .column(properties.getProperty(path).getColumn())
                        .value(String.format("%s.%s", expression, property.getName()))
                        .path(String.format("%s.%s", expression, property.getName()))
                        .attributes(Arrays.stream(criterion.attributes())
                                .collect(Collectors.toMap(Attribute::name, Attribute::value)))
                        .build();

                if (property.isAnnotationPresent(Function.class)) {
                    final Function function = property.getRequiredAnnotation(Function.class);
                    metadata.setColumn(FunctionHandlerRegistry.getInstance().get(function.handler()).handle(function, metadata));
                }

                final String value = CriterionHandlerRegistry.getInstance().get(criterion.handler()).handle(criterion, metadata);
                if (property.isAnnotationPresent(Offset.class)) {
                    offset = value;
                } else if (property.isAnnotationPresent(Limit.class)) {
                    limit = value;
                } else {
                    builder.append(value);
                }
            } else if (property.isAnnotationPresent(Offset.class)) {
                final String xml = Arrays.stream(property.getRequiredAnnotation(Offset.class).value()).map(String::trim).collect(Collectors.joining());
                final Metadata metadata = Metadata.builder()
                        .value(String.format("%s.%s", expression, property.getName()))
                        .path(String.format("%s.%s", expression, property.getName()))
                        .build();
                offset = Velocities.getValue(xml, metadata);
            } else if (property.isAnnotationPresent(Limit.class)) {
                final String xml = Arrays.stream(property.getRequiredAnnotation(Limit.class).value()).map(String::trim).collect(Collectors.joining());

                final Metadata metadata = Metadata.builder()
                        .value(String.format("%s.%s", expression, property.getName()))
                        .path(String.format("%s.%s", expression, property.getName()))
                        .build();
                limit = Velocities.getValue(xml, metadata);
            } else if (property.isAnnotationPresent(Criteria.class)) {

            }
        }
        builder.append("</where>");

        if (offset != null || limit != null) {

            builder.append("<trim prefix=\"LIMIT\">");
            if (offset != null) {
                builder.append(offset);
            }
            if (limit != null) {
                builder.append(limit);
            }
            builder.append("</trim>");
        }


        return builder.toString();
    }

}

