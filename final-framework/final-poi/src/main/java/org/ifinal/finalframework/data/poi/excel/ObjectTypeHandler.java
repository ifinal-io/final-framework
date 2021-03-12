package org.ifinal.finalframework.data.poi.excel;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinal.finalframework.util.Primaries;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;

/**
 * ObjectTypeHandler.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ObjectTypeHandler implements TypeHandler<Object> {

    @Override
    public void setValue(@NonNull final Cell cell, @Nullable final Object value) {
        if (Objects.isNull(value)) {
            return;
        }

        Class<?> clazz = value.getClass();
        String text = value.toString();
        if (Primaries.isNumber(clazz)) {
            cell.setCellValue(Double.parseDouble(text));
        } else if (Primaries.isBoolean(clazz)) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else {

            if (text.startsWith("=")) {
                cell.setCellFormula(text.substring(1));
            } else {
                cell.setCellValue(text);
            }
        }
    }

}
