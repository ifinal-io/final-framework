package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.TypeHandler;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.2.4
 **/
public class ShortTypeHandler implements TypeHandler<Short> {
    @Override
    public void setValue(@NonNull Cell cell, Short value) {
        cell.setCellValue(value);
    }

    @Override
    public Short getValue(@NonNull Cell cell) {
        switch (cell.getCellType()){
            case NUMERIC:
                return (short)cell.getNumericCellValue();
            case STRING:
                return Short.parseShort(cell.getStringCellValue());
            default:
                throw new IllegalArgumentException("Can not mapping Integer from " + cell.getCellType());
        }
    }
}
