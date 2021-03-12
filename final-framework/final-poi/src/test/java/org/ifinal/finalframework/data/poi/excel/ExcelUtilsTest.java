package org.ifinal.finalframework.data.poi.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.ifinal.finalframework.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

/**
 * ExcelUtilsTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ExcelUtilsTest {

    @Test
    void columnLabel() {
        assertEquals("A", ExcelUtils.columnLabel(0));
        assertEquals("Z", ExcelUtils.columnLabel(25));
        assertEquals("AA", ExcelUtils.columnLabel(26));
        assertEquals("AZ", ExcelUtils.columnLabel(51));
        assertEquals("BA", ExcelUtils.columnLabel(52));
        assertEquals("BZ", ExcelUtils.columnLabel(77));
        assertEquals("CA", ExcelUtils.columnLabel(78));
        assertEquals("DA", ExcelUtils.columnLabel(104));
        assertEquals("EA", ExcelUtils.columnLabel(130));
        assertEquals("FA", ExcelUtils.columnLabel(156));
        assertEquals("ZA", ExcelUtils.columnLabel(676));
        assertEquals("ZZ", ExcelUtils.columnLabel(701));
        assertEquals("AAA", ExcelUtils.columnLabel(702));
    }

    @Test
    void cellLabel(){
        assertEquals("A1",ExcelUtils.cellLabel(0,0));
    }

    @Test
    void mergeStyle() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("excel.json");

        InputStreamReader reader = new InputStreamReader(is);

        char[] buf = new char[1024 * 1024];

        int length = reader.read(buf);

        String json = new String(buf,0,length);

        Excel excel = Json.toObject(json, Excel.class);


    }

}
