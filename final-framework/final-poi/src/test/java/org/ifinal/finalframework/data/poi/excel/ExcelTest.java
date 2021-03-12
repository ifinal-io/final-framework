package org.ifinal.finalframework.data.poi.excel;

import org.ifinal.finalframework.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * ExcelTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ExcelTest {

    @Test
    void fromJson() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("excel.json");

        InputStreamReader reader = new InputStreamReader(is);

        char[] buf = new char[1024 * 1024];

        int length = reader.read(buf);

        String json = new String(buf, 0, length);

        Excel excel = Json.toObject(json, Excel.class);

        logger.info("{}", Json.toJson(excel));

        SpelExcelGenerator generator = new SpelExcelGenerator();
        generator.writeRow(excel.getSheets().get(0).getBody(), null);

        generator.write("base.xlsx");

    }

}
