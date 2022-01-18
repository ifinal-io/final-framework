package org.ifinalframework.poi.databind.deser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ifinalframework.poi.databind.ExcelDeserializer;
import org.ifinalframework.util.format.DateFormatters;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * Deserialize a {@link Date} result from {@link Cell}.
 *
 * @author likly
 * @version 1.2.4
 **/
public class DateExcelDeserializer implements ExcelDeserializer<Date> {

    @Nullable
    @Override
    public Date deserialize(@NonNull Cell cell) {

        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return null;

            case STRING:
                String value = cell.getStringCellValue();
                return DateFormatters.DEFAULT.parse(value);
            case _NONE:
            case BLANK:
                return null;
            default:
                throw new IllegalArgumentException("Can not mapping Date from " + cell.getCellType());


        }

    }
}
