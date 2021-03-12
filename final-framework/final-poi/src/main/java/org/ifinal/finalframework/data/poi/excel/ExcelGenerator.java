package org.ifinal.finalframework.data.poi.excel;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * ExcelGenerator.
 *
 * @author likly
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
