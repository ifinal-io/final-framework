package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.TypeHandler;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * @author likly
 * @version 1.2.4
 **/
public class BooleanTypeHandler implements TypeHandler<Boolean> {
    @Override
    public void setValue(@NonNull Cell cell, Boolean value) {
        cell.setCellValue(value);
    }

    @Override
    public Boolean getValue(Cell cell) {
        switch (cell.getCellType()){
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case STRING:
                return Boolean.valueOf(cell.getStringCellValue());
            case NUMERIC:
                return Objects.equals(1,(int)cell.getNumericCellValue());
            default:
                return null;
        }
    }
}
