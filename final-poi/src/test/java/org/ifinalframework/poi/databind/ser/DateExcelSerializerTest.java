package org.ifinalframework.poi.databind.ser;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

/**
 * @author likly
 * @version 1.2.4
 **/
@ExtendWith(MockitoExtension.class)
class DateExcelSerializerTest {

    private final DateExcelSerializer serializer = new DateExcelSerializer();

    @Mock
    private Cell cell;

    @Test
    void serialize() {
        Date date = new Date();
        serializer.serialize(cell, date);
        Mockito.verify(cell, Mockito.atLeastOnce()).setCellValue(Mockito.any(Date.class));

        ArgumentCaptor<Date> captor = ArgumentCaptor.forClass(Date.class);
        Mockito.verify(cell).setCellValue(captor.capture());
        Assertions.assertEquals(date, captor.getValue());

    }
}