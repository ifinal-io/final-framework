package org.ifinalframework.poi.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
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

        typeHandler.setValue(cell,value);
        verify(cell,only()).setCellValue(value);
        verify(cell).setCellValue(captor.capture());

        assertEquals(value,captor.getValue());
    }

    @Test
    void getValue() {
        // BOOLEAN
        when(cell.getCellType()).thenReturn(CellType.BOOLEAN);
        when(cell.getBooleanCellValue()).thenReturn(true);
        assertTrue(typeHandler.getValue(cell));

        // STRING
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("TRUE");
        assertTrue(typeHandler.getValue(cell));

        // NUMBER
        when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        when(cell.getNumericCellValue()).thenReturn(1.0);
        assertTrue(typeHandler.getValue(cell));


    }
}