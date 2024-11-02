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

package org.ifinalframework.data.util;

import org.springframework.core.annotation.AnnotationUtils;

import org.ifinalframework.data.annotation.NameConverter;
import org.ifinalframework.data.annotation.Table;
import org.ifinalframework.data.mapping.converter.CameHump2UnderlineNameConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TableUtils.
 *
 * @author iimik
 * @version 1.4.3
 * @since 1.4.3
 */
public abstract class TableUtils {

    private static final NameConverter NAME_CONVERTER = new CameHump2UnderlineNameConverter();

    public static String getTable(Class<?> clazz) {
        return getTables(clazz).get(0);
    }

    public static List<String> getTables(Class<?> clazz) {
        Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
        if (Objects.isNull(table) || table.value().length == 0) {
            return Collections.singletonList(NAME_CONVERTER.convert(clazz.getSimpleName()));
        }
        return Arrays.stream(table.value())
                .map(NAME_CONVERTER::convert)
                .collect(Collectors.toList());

    }

}
