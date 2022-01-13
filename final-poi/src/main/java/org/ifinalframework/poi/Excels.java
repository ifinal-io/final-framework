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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Excels.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Excels {

    private Excels() {
    }

    public static WorkbookWriter newWriter(final Excel.Cell... headers) {
        return newWriter(Arrays.asList(headers));
    }

    public static WorkbookWriter newWriter(final List<Excel.Cell> headers) {
        return newWriter(Excel.Version.XLSX, null, headers);
    }

    public static WorkbookWriter newWriter(final Excel.Version version, final String sheetName, List<Excel.Cell> headers) {

        final Excel excel = new Excel();
        excel.setVersion(version);

        Excel.Sheet sheet = new Excel.Sheet();
        sheet.setName(sheetName);
        sheet.setHeaders(Collections.singletonList(new Excel.Row(headers)));

        excel.setSheets(Collections.singletonList(sheet));


        return newWriter(excel);
    }

    public static WorkbookWriter newWriter(final Excel excel) {
        return new SpelExcelWriter(excel);
    }

    public static void write(final List<Excel.Cell> properties, final List<?> rows, final String filename) throws IOException {
        newWriter(properties).append(rows).write(filename);
    }

    public static void write(final List<Excel.Cell> properties, final List<?> rows, final OutputStream os) throws IOException {
        newWriter(properties).append(rows).write(os);
    }

}
