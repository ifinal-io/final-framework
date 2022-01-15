package org.ifinalframework.poi;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.2.4
 **/
@RequiredArgsConstructor
public class WorkbookReader {
    private final Workbook workbook;

    private int headerIndex = 0;

    public WorkbookReader skip(int header) {
        this.headerIndex = header;
        return this;
    }

    public <T> Stream<T> map(BiFunction<Row, Headers, T> function) {
        return IntStream.range(0, workbook.getNumberOfSheets())
                .mapToObj(workbook::getSheetAt)
                .flatMap(sheet -> new SheetReader(sheet).skip(headerIndex).map(function));
    }
}
