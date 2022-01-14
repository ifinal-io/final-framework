package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ifinalframework.poi.TypeHandler;
import org.ifinalframework.util.format.DateFormatters;
import org.ifinalframework.util.format.LocalDateTimeFormatters;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author likly
 * @version 1.2.4
 **/
public class LocalDateTimeTypeHandler implements TypeHandler<LocalDateTime> {

    @Override
    public void setValue(@NonNull Cell cell, LocalDateTime value) {
        cell.setCellValue(value);
    }

    @Override
    public LocalDateTime getValue(@NonNull Cell cell) {

        switch (cell.getCellType()) {
            case NUMERIC:
                if (!DateUtil.isCellDateFormatted(cell)) {
                   return cell.getLocalDateTimeCellValue();
                }
                break;

            case STRING:
                String value = cell.getStringCellValue();
                return LocalDateTimeFormatters.DEFAULT.parse(value);

        }

        return null;
    }
}
