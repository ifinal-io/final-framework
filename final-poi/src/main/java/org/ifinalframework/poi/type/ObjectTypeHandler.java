package org.ifinalframework.poi.type;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ifinalframework.poi.TypeHandler;

/**
 * @author likly
 * @version 1.2.4
 **/
public class ObjectTypeHandler implements TypeHandler<Object> {
    @Override
    public void setValue(Cell cell, Object value) {
        cell.setCellValue(value.toString());
    }

    @Override
    public Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:

                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue();
                }

                return cell.getNumericCellValue();
            case FORMULA:
                return cell.getCellFormula();

        }
        return null;
    }
}
