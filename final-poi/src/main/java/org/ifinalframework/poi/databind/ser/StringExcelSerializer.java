package org.ifinalframework.poi.databind.ser;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.poi.databind.ExcelSerializer;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author iimik
 * @version 1.2.4
 **/
public class StringExcelSerializer implements ExcelSerializer<String> {
    @Override
    public void serialize(@NonNull Cell cell, @Nullable String value) {
        cell.setCellValue(value);
    }
}
