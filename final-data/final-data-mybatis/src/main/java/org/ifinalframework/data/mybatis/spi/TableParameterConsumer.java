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

import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.IRepository;
import org.ifinalframework.data.util.TableUtils;

import java.lang.reflect.Method;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * TableMapParameterConsumer.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
@Component
public class TableParameterConsumer implements MapParameterConsumer {
    @Override
    public void accept(Map<String, Object> parameter, Class<?> mapper, Method method) {
        if (IRepository.class.isAssignableFrom(mapper)) {
            Class<?> entityClass = ResolvableType.forClass(mapper)
                    .as(IRepository.class)
                    .resolveGeneric(1);

            final String table = TableUtils.getTable(entityClass);
            if (parameter.containsKey("table")) {
                final boolean replaced = parameter.replace("table", null, table);
            } else {
                parameter.put("table", table);
            }
        }
    }
}
