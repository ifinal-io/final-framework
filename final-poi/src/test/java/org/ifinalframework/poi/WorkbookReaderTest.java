package org.ifinalframework.poi;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ifinalframework.poi.function.ResultMapBiFunction;
import org.ifinalframework.poi.mapping.ResultMap;
import org.ifinalframework.poi.mapping.ResultMapping;
import org.ifinalframework.poi.type.BooleanTypeHandler;
import org.ifinalframework.poi.type.DateTypeHandler;
import org.ifinalframework.poi.type.StringTypeHandler;
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
    void intStream(){
        long count = IntStream.range(0, 5).peek(it -> logger.info("{}",it)).count();
        Assertions.assertEquals(5,count);
    }

    @Test
    @SneakyThrows
    void test() {
        ResultMap<Person> personResultMap = new ResultMap<>(Person.class, Arrays.asList(
                new ResultMapping("name", "姓名", new StringTypeHandler()),
                new ResultMapping("birthday", "生日", new DateTypeHandler()),
                new ResultMapping("bool", "bool", new BooleanTypeHandler()),
                new ResultMapping("age", "年龄", new StringTypeHandler())
        ));

        XSSFWorkbook workbook = new XSSFWorkbook(getClass().getResourceAsStream("/excel.xlsx"));
        WorkbookReader reader = new WorkbookReader(workbook);


        List<Person> list = reader.map(new ResultMapBiFunction<Person>(personResultMap))
                .collect(Collectors.toList());


        System.out.println(list);

    } @Test
    @SneakyThrows
    void testMap() {
        ResultMap<Map> personResultMap = new ResultMap<>(Map.class, Arrays.asList());

        XSSFWorkbook workbook = new XSSFWorkbook(getClass().getResourceAsStream("/excel.xlsx"));
        WorkbookReader reader = new WorkbookReader(workbook);


        List<Map> list = reader.map(new ResultMapBiFunction<Map>(personResultMap))
                .collect(Collectors.toList());


        System.out.println(list);

    }

}