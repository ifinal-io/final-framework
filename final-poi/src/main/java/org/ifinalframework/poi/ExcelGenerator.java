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

package org.ifinalframework.poi;

import org.apache.poi.ss.usermodel.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ExcelGenerator.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ExcelGenerator {

    @NonNull
    CellStyle createCellStyle(@NonNull Excel.Style style);

    @NonNull
    Font createFont(@NonNull Excel.Font font);

    @NonNull
    Sheet createSheet(@NonNull Excel.Sheet sheet);

    @NonNull
    Row createRow(@NonNull Sheet sheet, @NonNull Excel.Row row);

    @NonNull
    Cell createCell(@NonNull Row row, @NonNull Excel.Cell cell);

    @Nullable
    CellStyle getCellStyle(@Nullable String style);

    @NonNull
    Sheet getSheet(int index);

    default void writeRow(@NonNull Excel.Row row, @Nullable Object data) {
        writeRow(0, row, data);
    }

    void writeRow(int index, @NonNull Excel.Row row, @Nullable Object data);

    void writeRow(@NonNull Sheet sheet, @NonNull Excel.Row row, @Nullable Object data);

    void writeCell(@NonNull Cell rowCell, @NonNull Excel.Cell cell, @Nullable Object data);

    void write(@NonNull OutputStream os) throws IOException;

    default void write(@NonNull String filename) throws IOException {
        write(new FileOutputStream(filename));
    }

}
