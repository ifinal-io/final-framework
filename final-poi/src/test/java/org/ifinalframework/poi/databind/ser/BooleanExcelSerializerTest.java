package org.ifinalframework.poi.databind.ser;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

/**
 * @author ilikly
 * @version 1.2.4
 **/
@ExtendWith(MockitoExtension.class)
class BooleanExcelSerializerTest {

    private final BooleanExcelSerializer serializer = new BooleanExcelSerializer();

    @Mock
    private Cell cell;

    @ParameterizedTest
    @NullSource
    @ValueSource(booleans = {true, false})
    void serialize(Boolean value) {
        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        serializer.serialize(cell, value);
        if (Objects.nonNull(value)) {
            Mockito.verify(cell, Mockito.atLeastOnce()).setCellValue(captor.capture());
            Assertions.assertEquals(value, captor.getValue());
        } else {
            Mockito.verify(cell, Mockito.never()).setCellValue(Mockito.anyBoolean());
        }


    }
}