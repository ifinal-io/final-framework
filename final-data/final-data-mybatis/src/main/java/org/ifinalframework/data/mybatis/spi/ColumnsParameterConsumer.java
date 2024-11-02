/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.mybatis.spi;

import org.springframework.stereotype.Component;

import org.ifinalframework.data.annotation.Metadata;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.QProperty;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.velocity.Velocities;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * ColumnsParameterConsumer
 *
 * @author iimik
 * @since 1.5.4
 **/
@Slf4j
@Component
public class ColumnsParameterConsumer implements MapParameterConsumer {
    private static final String DEFAULT_READER = "${column}";
    private static final String COLUMNS = "columns";

    @Override
    public void accept(Map<String, Object> parameter, Class<?> mapper, Method method) {
        final String methodName = method.getName();
        if (!methodName.startsWith("select")) {
            return;
        }

        final Class<?> entityClass = (Class<?>) parameter.get(EntityClassParameterConsumer.ENTITY_CLASS_PARAM_NAME);
        final QEntity<?, ?> properties = DefaultQEntityFactory.INSTANCE.create(entityClass);
        parameter.put("entity", properties);
        parameter.put("properties", properties);

        if (!parameter.containsKey(COLUMNS) || Objects.isNull(parameter.get(COLUMNS))) {
            if ("selectIds".equals(methodName)) {
                parameter.put(COLUMNS, Collections.singletonList(properties.getIdProperty().getColumn()));
            } else {
                parameter.put(COLUMNS, buildColumns(properties, parameter.containsKey("view")
                        ? (Class<?>) parameter.get("view") : null));
            }
        }


    }

    private List<String> buildColumns(QEntity<?, ?> entity, Class<?> view) {
        return entity.stream()
                .filter(QProperty::isReadable)
                .filter(it -> it.hasView(view))
                .map(property -> {
                    final Metadata metadata = new Metadata();
                    metadata.setProperty(property.getName());
                    metadata.setColumn(property.getColumn());
                    metadata.setValue(property.getName());
                    metadata.setJavaType(property.getType());
                    metadata.setTypeHandler(property.getTypeHandler());
                    final String reader = Asserts.isBlank(property.getReader()) ? DEFAULT_READER : property.getReader();
                    return Velocities.getValue(reader, metadata);
                }).collect(Collectors.toList());
    }
}
