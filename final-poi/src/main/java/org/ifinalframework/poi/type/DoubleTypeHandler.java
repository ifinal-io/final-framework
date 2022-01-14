package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.TypeHandler;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.2.4
 **/
public class DoubleTypeHandler implements TypeHandler<Double> {
    @Override
    public void setValue(@NonNull Cell cell, Double value) {
        cell.setCellValue(value);
    }

    @Override
    public Double getValue(@NonNull Cell cell) {
        switch (cell.getCellType()){
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return Double.parseDouble(cell.getStringCellValue());
            default:
                throw new IllegalArgumentException("Can not mapping Integer from " + cell.getCellType());
        }
    }
}
