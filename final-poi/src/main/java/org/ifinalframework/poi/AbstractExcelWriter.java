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

import org.ifinalframework.poi.model.Excel;
import org.ifinalframework.poi.model.Row;
import org.ifinalframework.poi.model.Style;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * AbstractExcelWriter.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbstractExcelWriter implements WorkbookWriter {

    private final Excel excel;

    private final Object data;

    private final ExcelGenerator generator;

    public AbstractExcelWriter(final Excel excel, Object data, final ExcelGenerator generator) {
        this.excel = excel;
        this.data = data;
        this.generator = generator;

        initStyles();
        initSheets();
    }

    private void initStyles() {
        List<Style> styles = Optional.ofNullable(excel.getStyles()).orElse(Collections.emptyList());
        styles.forEach(generator::createCellStyle);
    }

    private void initSheets() {
        for (final org.ifinalframework.poi.model.Sheet sheet : excel.getSheets()) {
            Sheet excelSheet = generator.createSheet(sheet);

            List<Row> rows = Optional.ofNullable(sheet.getHeaders()).orElse(Collections.emptyList());

            for (final Row row : rows) {
                generator.writeRow(excelSheet, row, data);
            }

        }
    }

    @Override
    public WorkbookWriter append(final int sheetIndex, final List<?> rows) {

        for (final Object row : rows) {
            generator.writeRow(sheetIndex, excel.getSheets().get(sheetIndex).getBody(), row);
        }

        return this;
    }

    @Override
    public void write(final OutputStream os) throws IOException {

        for (int i = 0; i < excel.getSheets().size(); i++) {

            org.ifinalframework.poi.model.Sheet sheet = excel.getSheets().get(i);
            Sheet excelSheet = generator.getSheet(i);
            List<Row> rows = Optional.ofNullable(sheet.getFooters()).orElse(Collections.emptyList());

            for (final Row row : rows) {
                generator.writeRow(excelSheet, row, null);
            }

            generator.addMergedRegions(i, sheet.getMergedRegions());


        }
        generator.write(os);
    }

}
