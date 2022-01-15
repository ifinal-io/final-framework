package org.ifinalframework.poi;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.2.4
 **/
@RequiredArgsConstructor
public class SheetReader {
    private final Sheet sheet;

    private int headerIndex;
    private Headers headers;

    public SheetReader skip(int header){
        this.headerIndex = header;
        this.headers = new Headers(header);

        Row row = sheet.getRow(header);
        int lastCellNum = row.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            headers.addHeader(i,row.getCell(i).getStringCellValue());
        }

        return this;
    }

    public <T> Stream<T> map(BiFunction<Row,Headers,T> function){
        return IntStream.rangeClosed(headerIndex + 1, sheet.getLastRowNum())
                .mapToObj(sheet::getRow)
                .map(row -> function.apply(row, headers));

    }


}
