/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.poi.function;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ifinalframework.poi.Headers;
import org.ifinalframework.poi.databind.TypeHandler;
import org.ifinalframework.poi.databind.type.ObjectTypeHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Apply a {@link Row} to a {@link Map} result.
 *
 * @author likly
 * @version 1.2.4
 * @see ObjectTypeHandler
 * @see ResultMapBiFunction
 **/
public class MapBiFunction implements BiFunction<Row, Headers, Map<String, Object>> {
    private final TypeHandler<Object> typeHandler = new ObjectTypeHandler();

    @Override
    public Map<String, Object> apply(Row row, Headers headers) {

        final Map<String, Object> map = new LinkedHashMap<>();

        headers.foreach((header, index) -> {
            Cell cell = row.getCell(index);
            Object value = Optional.ofNullable(cell).map(typeHandler::deserialize).orElse(null);
            map.put(header, value);
        });

        return map;
    }
}
