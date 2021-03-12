package org.ifinal.finalframework.data.poi.excel;

import org.ifinal.finalframework.data.poi.excel.Excel.Row;
import org.ifinal.finalframework.data.poi.excel.Excel.Style;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * AbstractExcelWriter.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbstractExcelWriter implements WorkbookWriter {

    private final Excel excel;

    private final ExcelGenerator generator;

    public AbstractExcelWriter(final Excel excel, final ExcelGenerator generator) {
        this.excel = excel;
        this.generator = generator;

        initStyles();
        initSheets();
    }

    private void initStyles() {
        List<Style> styles = Optional.ofNullable(excel.getStyles()).orElse(Collections.emptyList());
        styles.forEach(generator::createCellStyle);
    }

    private void initSheets() {
        for (final Excel.Sheet sheet : excel.getSheets()) {
            Sheet excelSheet = generator.createSheet(sheet);

            List<Row> rows = Optional.ofNullable(sheet.getHeaders()).orElse(Collections.emptyList());

            for (final Row row : rows) {
                generator.writeRow(excelSheet, row, null);
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

            Excel.Sheet sheet = excel.getSheets().get(i);
            Sheet excelSheet = generator.getSheet(i);
            List<Row> rows = Optional.ofNullable(sheet.getFooters()).orElse(Collections.emptyList());

            for (final Row row : rows) {
                generator.writeRow(excelSheet, row, null);
            }

        }
        generator.write(os);
    }

}
