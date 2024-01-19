package org.ifinalframework.poi.databind.deser;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.poi.databind.ExcelDeserializer;
import org.ifinalframework.util.format.LocalDateTimeFormatters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDateTime;

/**
 * Deserialize a {@link LocalDateTime} result from {@link Cell}.
 *
 * @author iimik
 * @version 1.2.4
 **/
public class LocalDateTimeExcelDeserializer implements ExcelDeserializer<LocalDateTime> {

    @Nullable
    @Override
    public LocalDateTime deserialize(@NonNull Cell cell) {

        switch (cell.getCellType()) {
            case NUMERIC:
                if (!DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue();
                }
                return null;

            case STRING:
                String value = cell.getStringCellValue();
                return LocalDateTimeFormatters.DEFAULT.parse(value);
            case _NONE:
            case BLANK:
                return null;
            default:
                throw new IllegalArgumentException("Can not mapping LocalDateTime from " + cell.getCellType());


        }

    }
}
