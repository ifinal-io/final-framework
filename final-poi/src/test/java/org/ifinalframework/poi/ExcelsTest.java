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

package org.ifinalframework.poi;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ifinalframework.poi.model.Cell;
import org.ifinalframework.poi.model.Excel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * ExcelsTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
class ExcelsTest {

    private List<Person> persons() {
        return Arrays.asList(
                new Person("xiaoMing", 12, new Date(), true),
                new Person("xiaoHong", 18, new Date(), false)
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

        Assertions.assertDoesNotThrow(() -> {

            InputStream is = getClass().getClassLoader().getResourceAsStream("excel.json");

            InputStreamReader reader = new InputStreamReader(is);

            char[] buf = new char[1024 * 1024];

            int length = reader.read(buf);

            String json = new String(buf, 0, length);

            ObjectMapper objectMapper = new ObjectMapper();

            Excel excel = objectMapper.readValue(json, Excel.class);

            Excels.newWriter(excel).append(persons())
                    .append(persons())
                    .write("target/test3.xlsx");
        });

    }

}
