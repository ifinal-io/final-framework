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

import org.ifinalframework.poi.function.MapBiFunction;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ilikly
 * @version 1.2.4
 **/
public class WorkbookReader {

    private static final MapBiFunction MAP_BI_FUNCTION = new MapBiFunction();

    private final Workbook workbook;
    private final int headerIndex;

    public WorkbookReader(InputStream in) throws IOException {
        this(in, 0);
    }

    public WorkbookReader(InputStream in, int headerIndex) throws IOException {
        this(new XSSFWorkbook(in), headerIndex);
    }

    public WorkbookReader(Workbook workbook) {
        this(workbook, 0);
    }

    public WorkbookReader(Workbook workbook, int headerIndex) {
        this.workbook = workbook;
        this.headerIndex = headerIndex;
    }

    /**
     * Apply all sheets to a {@link Map} result stream.
     *
     * @see MapBiFunction
     */
    public Stream<Map<String, Object>> map() {
        return map(MAP_BI_FUNCTION);
    }

    /**
     * Apply all sheets to a {@link T} result stream.
     *
     * @param function apply function.
     * @param <T>      apply result.
     * @see SheetReader#map(BiFunction)
     */
    public <T> Stream<T> map(BiFunction<Row, Headers, T> function) {
        return IntStream.range(0, workbook.getNumberOfSheets())
                .mapToObj(workbook::getSheetAt)
                .flatMap(sheet -> new SheetReader(sheet, headerIndex).map(function));
    }

    /**
     * Apply the sheet at {@link Workbook#getSheetAt(int)} to a {@link T} result stream.
     *
     * @param sheet    the sheet position.
     * @param function apply function.
     * @param <T>      apply result.
     * @see SheetReader#map(BiFunction)
     */
    public <T> Stream<T> map(int sheet, BiFunction<Row, Headers, T> function) {
        return new SheetReader(workbook.getSheetAt(sheet), headerIndex).map(function);
    }

    private static class SheetReader {
        private final Sheet sheet;

        private final int headerIndex;
        private final Headers headers;

        SheetReader(Sheet sheet, int headerIndex) {
            this.sheet = sheet;
            this.headerIndex = headerIndex;
            this.headers = new Headers(headerIndex);

            Row row = sheet.getRow(headerIndex);
            int lastCellNum = row.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                headers.addHeader(i, row.getCell(i).getStringCellValue());
            }
        }

        <T> Stream<T> map(BiFunction<Row, Headers, T> function) {
            return IntStream.rangeClosed(headerIndex + 1, sheet.getLastRowNum())
                    .mapToObj(sheet::getRow)
                    .map(row -> function.apply(row, headers));

        }

    }

}
