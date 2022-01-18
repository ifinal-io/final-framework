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

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.poi.databind.type.BooleanTypeHandler;
import org.ifinalframework.poi.databind.type.DateTypeHandler;
import org.ifinalframework.poi.databind.type.IntegerTypeHandler;
import org.ifinalframework.poi.databind.type.StringTypeHandler;
import org.ifinalframework.poi.function.ResultMapBiFunction;
import org.ifinalframework.poi.mapping.ResultMap;
import org.ifinalframework.poi.mapping.ResultMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author likly
 * @version 1.2.4
 **/
@Slf4j
class WorkbookReaderTest {

    @Test
    void intStream() {
        long count = IntStream.range(0, 5).peek(it -> logger.info("{}", it)).count();
        Assertions.assertEquals(5, count);
    }

    @Test
    @SneakyThrows
    void test() {
        ResultMap<Person> personResultMap = new ResultMap<>(Person.class, Arrays.asList(
                new ResultMapping("name", "姓名", new StringTypeHandler()),
                new ResultMapping("birthday", "生日", new DateTypeHandler()),
                new ResultMapping("bool", "bool", new BooleanTypeHandler()),
                new ResultMapping("age", "年龄", new IntegerTypeHandler())
        ));

        WorkbookReader reader = new WorkbookReader(getClass().getResourceAsStream("/excel.xlsx"));


        List<Person> list = reader.map(new ResultMapBiFunction<Person>(personResultMap))
                .collect(Collectors.toList());


        System.out.println(list);

    }

    @Test
    @SneakyThrows
    void testMap() {

        WorkbookReader reader = new WorkbookReader(getClass().getResourceAsStream("/excel.xlsx"));

        List<Map> list = reader.map().collect(Collectors.toList());

        System.out.println(list);

    }

}