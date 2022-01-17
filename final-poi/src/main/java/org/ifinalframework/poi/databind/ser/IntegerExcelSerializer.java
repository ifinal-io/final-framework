package org.ifinalframework.poi.databind.ser;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.databind.ExcelSerializer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author likly
 * @version 1.2.4
 **/
public class IntegerExcelSerializer implements ExcelSerializer<Integer> {
    @Override
    public void serialize(@NonNull Cell cell, @Nullable Integer value) {
        Optional.ofNullable(value).ifPresent(cell::setCellValue);
    }
}
