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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ifinalframework.poi.Excel.Style;
import org.ifinalframework.poi.databind.TypeHandler;
import org.ifinalframework.poi.databind.type.ObjectTypeHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AbstractExcelGenerator.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractExcelGenerator implements ExcelGenerator {

    private final Map<String, CellStyle> styles = new ConcurrentHashMap<>();

    private final Workbook workbook;

    public AbstractExcelGenerator() {
        this(new XSSFWorkbook());
    }

    public AbstractExcelGenerator(final Workbook workbook) {
        this.workbook = workbook;
    }

    @NonNull
    @Override
    public CellStyle createCellStyle(@NonNull final Style style) {

        CellStyle cellStyle = getWorkbook().createCellStyle();

        Optional.ofNullable(style.getLocked()).ifPresent(cellStyle::setLocked);
        Optional.ofNullable(style.getHorizontalAlignment()).ifPresent(cellStyle::setAlignment);
        Optional.ofNullable(style.getVerticalAlignment()).ifPresent(cellStyle::setVerticalAlignment);

        if (cellStyle instanceof XSSFCellStyle) {
            Optional.ofNullable(style.getForegroundColor()).ifPresent(color -> {
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                ((XSSFCellStyle) cellStyle).setFillForegroundColor(new XSSFColor(Colors.decode(color)));
            });
        }

        if (Objects.nonNull(style.getFont())) {
            cellStyle.setFont(createFont(style.getFont()));
        }

        styles.put(Objects.requireNonNull(style.getTitle(), "style title can not be null."), cellStyle);

        return cellStyle;
    }

    @NonNull
    @Override
    public Font createFont(@NonNull final Excel.Font font) {
        Font workbookFont = getWorkbook().createFont();
        Optional.ofNullable(font.getName()).ifPresent(workbookFont::setFontName);
        Optional.ofNullable(font.getSize()).ifPresent(workbookFont::setFontHeightInPoints);

        if (workbookFont instanceof XSSFFont) {
            Optional.ofNullable(font.getColor()).ifPresent(color -> {
                ((XSSFFont) workbookFont).setColor(new XSSFColor(Colors.decode(color)));

            });
        }

        return workbookFont;
    }

    @NonNull
    @Override
    public Sheet createSheet(@NonNull final Excel.Sheet sheet) {
        String sheetName = sheet.getName();
        final Sheet excelSheet = Objects.isNull(sheetName) ? workbook.createSheet() : workbook.createSheet(sheetName);

        Optional.ofNullable(sheet.getDefaultRowHeight()).ifPresent(excelSheet::setDefaultRowHeightInPoints);
        Optional.ofNullable(sheet.getDefaultColumnWidth())
                .ifPresent(it -> excelSheet.setDefaultColumnWidth(it == -1 ? -1 : (int) (it * 20)));

        return excelSheet;
    }

    @NonNull
    @Override
    public Row createRow(@NonNull final Sheet sheet, @NonNull final Excel.Row row) {

        final Row sheetRow = sheet.createRow(sheet.getLastRowNum() + 1);

        Optional.ofNullable(row.getHeight()).ifPresent(sheetRow::setHeightInPoints);
        Optional.ofNullable(getCellStyle(row.getStyle())).ifPresent(sheetRow::setRowStyle);

        return sheetRow;
    }

    @NonNull
    @Override
    public Cell createCell(@NonNull final Row row, @NonNull final Excel.Cell cell) {

        int columnIndex = Optional.ofNullable(cell.getIndex()).orElse((int) row.getLastCellNum());
        if (columnIndex < 0) {
            columnIndex = 0;
        }

        Cell rowCell = row.createCell(columnIndex);

        Optional.ofNullable(cell.getColumnWidth()).ifPresent(width ->
                rowCell.getSheet().setColumnWidth(rowCell.getColumnIndex(), width * 256)

        );

        Optional.ofNullable(getCellStyle(cell.getStyle())).ifPresent(rowCell::setCellStyle);

        return rowCell;
    }

    @NonNull
    protected Workbook getWorkbook() {
        return this.workbook;
    }

    @Nullable
    @Override
    public CellStyle getCellStyle(@Nullable final String style) {
        return Optional.ofNullable(style).map(styles::get).orElse(null);
    }

    @NonNull
    @Override
    public Sheet getSheet(final int index) {
        return getWorkbook().getSheetAt(index);
    }

    @Override
    public void writeRow(final int index, @NonNull final Excel.Row row, @Nullable final Object data) {
        writeRow(getWorkbook().getSheetAt(index), row, data);
    }

    @Override
    public void writeRow(@NonNull final Sheet sheet, @NonNull final Excel.Row row, @Nullable final Object data) {
        Row sheetRow = createRow(sheet, row);
        for (final Excel.Cell cell : row.getCells()) {
            Cell rowCell = createCell(sheetRow, cell);
            writeCell(rowCell, cell, data);
        }
    }

    @Override
    public void writeCell(@NonNull final Cell rowCell, @NonNull final Excel.Cell cell, @Nullable final Object data) {

        Object value = calcCellValue(rowCell, cell, data, rowCell.getCellType());

        TypeHandler<Object> typeHandler = new ObjectTypeHandler();

        typeHandler.serialize(rowCell, value);

    }

    protected abstract Object calcCellValue(Cell rowCell, Excel.Cell cell, Object data, CellType type);

    @Override
    public void write(@NonNull final OutputStream os) throws IOException {
        getWorkbook().write(os);
        getWorkbook().close();
        os.close();
    }

}
