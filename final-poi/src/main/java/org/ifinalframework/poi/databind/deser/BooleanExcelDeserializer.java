package org.ifinalframework.poi.databind.deser;

import org.apache.poi.ss.usermodel.Cell;
import org.ifinalframework.poi.databind.ExcelDeserializer;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.2.4
 **/
public class BooleanExcelDeserializer implements ExcelDeserializer<Boolean> {
    private static final Set<String> TRUE_STRINGS = Stream.of("TRUE", "T", "YES", "Y").collect(Collectors.toSet());

    @Override
    public Boolean deserialize(@NonNull Cell cell) {
        switch (cell.getCellType()) {
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case STRING:
                return TRUE_STRINGS.contains(cell.getStringCellValue().toUpperCase());
            case NUMERIC:
                return Objects.equals(cell.getNumericCellValue(), 1.0);
            case _NONE:
            case BLANK:
                return null;
            default:
                throw new IllegalArgumentException("Can not mapping Boolean from " + cell.getCellType());
        }
    }
}
