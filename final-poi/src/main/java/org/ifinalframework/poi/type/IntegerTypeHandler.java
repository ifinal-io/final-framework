package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.TypeHandler;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.2.4
 **/
public class IntegerTypeHandler implements TypeHandler<Integer> {
    @Override
    public void setValue(@NonNull Cell cell, Integer value) {
        cell.setCellValue(value);
    }

    @Override
    public Integer getValue(@NonNull Cell cell) {
        switch (cell.getCellType()){
            case NUMERIC:
                return (int)cell.getNumericCellValue();
            case STRING:
                return Integer.parseInt(cell.getStringCellValue());
            default:
                throw new IllegalArgumentException("Can not mapping Integer from " + cell.getCellType());
        }
    }
}
