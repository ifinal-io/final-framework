package org.ifinalframework.poi.databind.ser;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.databind.ExcelSerializer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author ilikly
 * @version 1.2.4
 **/
public class LocalDateTimeExcelSerializer implements ExcelSerializer<LocalDateTime> {
    @Override
    public void serialize(@NonNull Cell cell, @Nullable LocalDateTime value) {
        Optional.ofNullable(value).ifPresent(cell::setCellValue);
    }
}
