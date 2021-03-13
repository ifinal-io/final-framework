package org.ifinal.finalframework.data.poi.excel;

import org.ifinal.finalframework.data.poi.excel.Excel.Cell;
import org.ifinal.finalframework.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * ExcelsTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ExcelsTest {

    private List<Person> persons() {
        return Arrays.asList(
            new Person("xiaoMing", 12, new Date()),
            new Person("xiaoHong", 18, new Date())
        );
    }

    private List<Cell> cells() {
        List<Cell> cells = new ArrayList<>();
//        cells.add(new Cell(0, "姓名", "name"));
//        cells.add(new Cell(2, "生日", "new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(birthday)"));
//        cells.add(new Cell(1, "年龄", "age"));
        return cells;
    }


    @Test
    void testJson() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("excel.json");

        InputStreamReader reader = new InputStreamReader(is);

        char[] buf = new char[1024 * 1024];

        int length = reader.read(buf);

        String json = new String(buf,0,length);



        Excel excel = Json.toObject(json, Excel.class);

        Excels.newWriter(excel).append(persons())
            .append(persons())
            .write("test3.xlsx");

    }

}
