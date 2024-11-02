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
 * @author iimik
 * @version 1.2.4
 **/
@ExtendWith(MockitoExtension.class)
class StringExcelDeserializerTest {
    private final StringExcelDeserializer deserializer = new StringExcelDeserializer();

    @Mock
    private Cell cell;

    @Test
    void getStringValue() {
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("haha");
        assertEquals("haha", deserializer.deserialize(cell));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void getBooleanValue(Boolean value) {
        when(cell.getCellType()).thenReturn(CellType.BOOLEAN);
        when(cell.getBooleanCellValue()).thenReturn(value);
        assertEquals(value.toString(), deserializer.deserialize(cell));
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class, names = {"_NONE", "BLANK"})
    void getNullValue(CellType cellType) {
        when(cell.getCellType()).thenReturn(cellType);
        assertNull(deserializer.deserialize(cell));
    }

}