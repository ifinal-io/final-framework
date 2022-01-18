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

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ifinalframework.poi.Headers;
import org.ifinalframework.poi.databind.ExcelDeserializer;
import org.ifinalframework.poi.mapping.ResultMap;
import org.ifinalframework.poi.mapping.ResultMapping;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Apply a {@link Row} to a {@link T} result with {@link ResultMap}.
 *
 * @author likly
 * @version 1.2.4
 * @see MapBiFunction
 **/
@RequiredArgsConstructor
public class ResultMapBiFunction<T> implements BiFunction<Row, Headers, T> {

    private final ResultMap<T> resultMap;

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public T apply(Row row, Headers headers) {

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(resultMap.getJavaType());

        for (ResultMapping resultMapping : resultMap.getResultMappings()) {

            Integer index = resultMapping.getIndex();

            if (Objects.isNull(index)) {
                index = headers.getHeaderIndex(resultMapping.getColumn());
            }

            Cell cell = row.getCell(index);

            if (Objects.isNull(cell)) {
                continue;
            }

            ExcelDeserializer deserializer = resultMapping.getDeserializer();
            Object value = deserializer.deserialize(cell);
            beanWrapper.setPropertyValue(resultMapping.getProperty(), value);

        }

        return (T) beanWrapper.getWrappedInstance();

    }
}
