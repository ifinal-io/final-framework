/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
class LongExcelDeserializerTest {

    private final LongExcelDeserializer deserializer = new LongExcelDeserializer();

    @Mock
    private Cell cell;

    @Test
    void getNumberValue() {
        when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        when(cell.getNumericCellValue()).thenReturn(1.0);
        assertEquals(1, deserializer.deserialize(cell));
    }

    @Test
    void getStringValue() {
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("1.1");
        assertEquals(1, deserializer.deserialize(cell));
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void getBooleanValue(Boolean value) {
        when(cell.getCellType()).thenReturn(CellType.BOOLEAN);
        when(cell.getBooleanCellValue()).thenReturn(value);
        assertEquals(value ? 1 : 0, deserializer.deserialize(cell));
    }

    @EnumSource(value = CellType.class, names = {"_NONE", "BLANK"})
    @ParameterizedTest
    void getNullValue(CellType cellType) {
        when(cell.getCellType()).thenReturn(cellType);
        assertNull(deserializer.deserialize(cell));
    }

    @EnumSource(value = CellType.class, names = {"ERROR"})
    @ParameterizedTest
    void exception(CellType cellType) {
        when(cell.getCellType()).thenReturn(cellType);
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(cell));
    }
}