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
import org.ifinalframework.util.format.LocalDateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * Deserialize a {@link String} result from {@link Cell}.
 *
 * @author iimik
 * @version 1.2.4
 * @since 1.2.4
 */
public class StringExcelDeserializer implements ExcelDeserializer<String> {
    @Nullable
    @Override
    public String deserialize(@NonNull Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS.format(cell.getLocalDateTimeCellValue());
                }
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            case _NONE:
                return null;
            default:
                throw new IllegalArgumentException("Can not mapping String from " + cell.getCellType());
        }
    }
}
