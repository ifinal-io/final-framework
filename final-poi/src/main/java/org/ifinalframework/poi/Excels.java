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

import org.ifinalframework.poi.model.Cell;
import org.ifinalframework.poi.model.Excel;
import org.ifinalframework.poi.model.Row;
import org.ifinalframework.poi.model.Sheet;
import org.ifinalframework.poi.model.Version;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Excels.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Excels {

    private Excels() {
    }

    /**
     * return a work book reader.
     *
     * @param is work boot input stream.
     * @return a work book reader
     * @throws IOException io exception.
     * @since 1.3.1
     */
    public static WorkbookReader newReader(InputStream is) throws IOException {
        return new WorkbookReader(is);
    }

    /**
     * return a work book reader.
     *
     * @param workbook a work book.
     * @return a work book reader.
     * @since 1.3.1
     */
    public static WorkbookReader newReader(Workbook workbook) {
        return new WorkbookReader(workbook);
    }

    public static WorkbookWriter newWriter(final Cell... headers) {
        return newWriter(Arrays.asList(headers));
    }

    public static WorkbookWriter newWriter(final List<Cell> headers) {
        return newWriter(Version.XLSX, null, headers);
    }

    public static WorkbookWriter newWriter(final Version version, final String sheetName, List<Cell> headers) {

        final Excel excel = new Excel();
        excel.setVersion(version);

        Sheet sheet = new Sheet();
        sheet.setName(sheetName);
        sheet.setHeaders(Collections.singletonList(new Row(headers)));

        excel.setSheets(Collections.singletonList(sheet));


        return newWriter(excel);
    }

    public static WorkbookWriter newWriter(final Excel excel) {
        return newWriter(excel, null);
    }

    public static WorkbookWriter newWriter(Excel excel, Object data) {
        return new SpelExcelWriter(excel, data);
    }

    public static void write(final List<Cell> properties, final List<?> rows, final String filename) throws IOException {
        newWriter(properties).append(rows).write(filename);
    }

    public static void write(final List<Cell> properties, final List<?> rows, final OutputStream os) throws IOException {
        newWriter(properties).append(rows).write(os);
    }

}
