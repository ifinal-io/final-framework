package org.ifinal.finalframework.data.poi.excel;

import org.ifinal.finalframework.data.poi.excel.Excel.Cell;
import org.ifinal.finalframework.data.poi.excel.Excel.Sheet;
import org.ifinal.finalframework.data.poi.excel.Excel.Version;

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

    public static WorkbookWriter newWriter(final List<Cell>... headers) {
        return newWriter(Arrays.asList(headers));
    }

    public static WorkbookWriter newWriter(final List<List<Cell>> headers) {
        return newWriter(Version.XLSX, null, headers);
    }

    public static WorkbookWriter newWriter(final Version version, final String sheetName, List<List<Cell>> headers) {

        final Excel excel = new Excel();
        excel.setVersion(version);

        Sheet sheet = new Sheet();
        sheet.setName(sheetName);

//        sheet.setHeaders(headers.stream().map(Row::new).collect(Collectors.toList()));

        excel.setSheets(Collections.singletonList(sheet));

        return newWriter(excel);
    }

    public static WorkbookWriter newWriter(final Excel excel) {
        return new SpelExcelWriter(excel);
    }

    public static void write(final List<Cell> properties, final List<?> rows, final String filename) throws IOException {
        newWriter(properties).append(rows).write(filename);
    }

    public static void write(final List<Cell> properties, final List<?> rows, final OutputStream os) throws IOException {
        newWriter(properties).append(rows).write(os);
    }

}
