package org.ifinalframework.poi.databind.deser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author ilikly
 * @version 1.2.4
 **/
@ExtendWith(MockitoExtension.class)
class FloatExcelDeserializerTest {

    private final FloatExcelDeserializer deserializer = new FloatExcelDeserializer();

    @Mock
    private Cell cell;

    @Test
    void getNumberValue() {
        when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        when(cell.getNumericCellValue()).thenReturn(1.1);
        assertEquals(1.1F, deserializer.deserialize(cell));
    }

    @Test
    void getStringValue() {
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("1.1");
        assertEquals(1.1F, deserializer.deserialize(cell));
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void getBooleanValue(Boolean value) {
        when(cell.getCellType()).thenReturn(CellType.BOOLEAN);
        when(cell.getBooleanCellValue()).thenReturn(value);
        assertEquals(value ? 1.0F : 0.0F, deserializer.deserialize(cell));
    }

    @EnumSource(value = CellType.class, names = {"_NONE", "BLANK"})
    @ParameterizedTest
    void getNullValue(CellType cellType) {
        when(cell.getCellType()).thenReturn(cellType);
        assertNull(deserializer.deserialize(cell));
    }

    @EnumSource(value = CellType.class, names = {"FORMULA", "ERROR"})
    @ParameterizedTest
    void exception(CellType cellType) {
        when(cell.getCellType()).thenReturn(cellType);
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(cell));
    }

}