package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ifinalframework.poi.TypeHandler;
import org.ifinalframework.util.format.DateFormatters;
import org.springframework.lang.NonNull;

import java.util.Date;

/**
 * @author likly
 * @version 1.2.4
 **/
public class DateTypeHandler implements TypeHandler<Date> {

    @Override
    public void setValue(@NonNull Cell cell, Date value) {
        cell.setCellValue(value);
    }

    @Override
    public Date getValue(@NonNull Cell cell) {

        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                   return cell.getDateCellValue();
                }
                break;

            case STRING:
                String value = cell.getStringCellValue();
                return DateFormatters.DEFAULT.parse(value);


        }

        return null;
    }
}
