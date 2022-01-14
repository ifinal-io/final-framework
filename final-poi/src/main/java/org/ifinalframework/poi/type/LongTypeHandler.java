package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.TypeHandler;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.2.4
 **/
public class LongTypeHandler implements TypeHandler<Long> {
    @Override
    public void setValue(@NonNull Cell cell, Long value) {
        cell.setCellValue(value);
    }

    @Override
    public Long getValue(@NonNull Cell cell) {
        switch (cell.getCellType()){
            case NUMERIC:
                return (long)cell.getNumericCellValue();
            case STRING:
                return Long.parseLong(cell.getStringCellValue());
            default:
                throw new IllegalArgumentException("Can not mapping Integer from " + cell.getCellType());
        }
    }
}
