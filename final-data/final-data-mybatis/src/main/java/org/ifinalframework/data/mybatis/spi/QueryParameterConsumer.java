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
import org.springframework.util.CollectionUtils;

import org.ifinalframework.core.Groupable;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.Limitable;
import org.ifinalframework.core.Orderable;
import org.ifinalframework.core.Viewable;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.QEntity;
import org.ifinalframework.data.query.QEntityFactory;
import org.ifinalframework.data.query.QProperty;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * QueryParameterConsumer.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Component
public class QueryParameterConsumer implements MapParameterConsumer {
    private static final String QUERY_PARAM_NAME = "query";
    private final QEntityFactory entityFactory = DefaultQEntityFactory.INSTANCE;

    @Override
    public void accept(Map<String, Object> parameter, Class<?> mapper, Method method) {
        if (!parameter.containsKey(QUERY_PARAM_NAME)) {
            return;
        }
        final Object query = parameter.get(QUERY_PARAM_NAME);

        if (Objects.isNull(query)) {
            return;
        }

        if (!(query instanceof IQuery)) {
            return;
        }

        final Class<?> entityClass = (Class<?>) parameter.get(EntityClassParameterConsumer.ENTITY_CLASS_PARAM_NAME);

        if (Objects.nonNull(query) && parameter.containsKey("view") && query instanceof Viewable && Objects.isNull(parameter.get("view"))) {
            parameter.put("view", ((Viewable) query).getView());
        }


        if (query instanceof Orderable) {
            Orderable orderable = (Orderable) query;
            List<String> orders = orderable.getOrders();
            if (!CollectionUtils.isEmpty(orders)) {
                QEntity<?, ?> qentity = entityFactory.create(entityClass);

                List<String> newOrders = orders.stream()
                        .map(it -> {
                            String[] split = it.split(" ");
                            String property = split[0];
                            String direction = split.length == 2 ? split[1] : null;

                            QProperty<Object> requiredProperty = qentity.getRequiredProperty(property);

                            return Stream.of(requiredProperty.getColumn(), direction)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.joining(" "));

                        })
                        .collect(Collectors.toList());

                parameter.put("orders", newOrders);

            } else {
                parameter.put("orders", null);
            }
        }

        if (query instanceof Groupable) {
            Groupable groupable = (Groupable) query;
            parameter.put("groups", groupable.getGroups());
        }

        if (query instanceof Limitable) {
            parameter.put("limit", query);
        }

    }
}
