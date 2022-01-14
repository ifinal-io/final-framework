package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.TypeHandler;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.2.4
 **/
public class FloatTypeHandler implements TypeHandler<Float> {
    @Override
    public void setValue(@NonNull Cell cell, Float value) {
        cell.setCellValue(value);
    }

    @Override
    public Float getValue(@NonNull Cell cell) {
        switch (cell.getCellType()){
            case NUMERIC:
                return (float)cell.getNumericCellValue();
            case STRING:
                return Float.parseFloat(cell.getStringCellValue());
            default:
                throw new IllegalArgumentException("Can not mapping Integer from " + cell.getCellType());
        }
    }
}
