package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author likly
 * @version 1.2.4
 **/
@ExtendWith(MockitoExtension.class)
class BooleanTypeHandlerTest {

    private BooleanTypeHandler typeHandler = new BooleanTypeHandler();

    @Mock
    private Cell cell;

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    void setValue(Boolean value) {

        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);

        typeHandler.serialize(cell, value);
        verify(cell,only()).setCellValue(value);
        verify(cell).setCellValue(captor.capture());

        assertEquals(value,captor.getValue());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    void getBooleanString(Boolean value){
        when(cell.getCellType()).thenReturn(CellType.BOOLEAN);
        when(cell.getBooleanCellValue()).thenReturn(value);
        assertEquals(value, typeHandler.deserialize(cell));
    }

    @ParameterizedTest
    @ValueSource(strings = {"YES","Y","TRUE","T","yes","y","true","t"})
    void getStringValue(String value){
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn(value);
        assertTrue(typeHandler.deserialize(cell));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0,1.00,1.000})
    void getNumberValue(Double value){
        when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        when(cell.getNumericCellValue()).thenReturn(value);
        assertTrue(typeHandler.deserialize(cell));
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class,names = {"_NONE","BLANK"})
    void getNullValue(CellType cellType){
        when(cell.getCellType()).thenReturn(cellType);
        assertNull(typeHandler.deserialize(cell));
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class,names = {"ERROR","FORMULA"})
    void getException(CellType cellType){
        when(cell.getCellType()).thenReturn(cellType);
        assertThrows(IllegalArgumentException.class, () -> typeHandler.deserialize(cell));
    }

}