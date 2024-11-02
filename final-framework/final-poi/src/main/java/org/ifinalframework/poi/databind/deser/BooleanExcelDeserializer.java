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

package org.ifinalframework.poi.databind.deser;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.poi.databind.ExcelDeserializer;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Deserialize a {@link Boolean} result from {@link Cell}.
 *
 * @author iimik
 * @version 1.2.4
 **/
public class BooleanExcelDeserializer implements ExcelDeserializer<Boolean> {
    private static final Set<String> TRUE_STRINGS = Stream.of("TRUE", "T", "YES", "Y").collect(Collectors.toSet());

    @Override
    @Nullable
    public Boolean deserialize(@NonNull Cell cell) {
        switch (cell.getCellType()) {
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case STRING:
                return TRUE_STRINGS.contains(cell.getStringCellValue().toUpperCase());
            case NUMERIC:
                return Objects.equals(cell.getNumericCellValue(), 1.0);
            case _NONE:
            case BLANK:
                return null;
            default:
                throw new IllegalArgumentException("Can not mapping Boolean from " + cell.getCellType());
        }
    }
}
