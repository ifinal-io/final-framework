package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.TypeHandler;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.2.4
 **/
public class StringTypeHandler implements TypeHandler<String> {
    @Override
    public void setValue(@NonNull Cell cell, String value) {
        cell.setCellValue(value);
    }

    @Override
    public String getValue(@NonNull Cell cell) {
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
        }
        return null;
    }
}
