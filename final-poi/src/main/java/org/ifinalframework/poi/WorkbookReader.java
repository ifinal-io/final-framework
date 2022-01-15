package org.ifinalframework.poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.2.4
 **/
public class WorkbookReader {
    private final Workbook workbook;
    private final int headerIndex;

    public WorkbookReader(Workbook workbook) {
        this(workbook, 0);
    }

    public WorkbookReader(Workbook workbook, int headerIndex) {
        this.workbook = workbook;
        this.headerIndex = headerIndex;
    }

    public <T> Stream<T> map(BiFunction<Row, Headers, T> function) {
        return IntStream.range(0, workbook.getNumberOfSheets())
                .mapToObj(workbook::getSheetAt)
                .flatMap(sheet -> new SheetReader(sheet,headerIndex).map(function));
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
