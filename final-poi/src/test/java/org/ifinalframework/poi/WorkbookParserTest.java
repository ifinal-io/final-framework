package org.ifinalframework.poi;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ifinalframework.poi.handler.ResultMapHandler;
import org.ifinalframework.poi.mapping.ResultMap;
import org.ifinalframework.poi.mapping.ResultMapping;
import org.ifinalframework.poi.type.BooleanTypeHandler;
import org.ifinalframework.poi.type.DateTypeHandler;
import org.ifinalframework.poi.type.StringTypeHandler;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.2.4
 **/
class WorkbookParserTest {

    private final WorkbookParser workbookParser = new WorkbookParser();

    @Test
    @SneakyThrows
    void parse() {
        XSSFWorkbook workbook = new XSSFWorkbook(getClass().getResourceAsStream("/excel.xlsx"));

        ResultMap<Person> personResultMap = new ResultMap<>(Person.class, Arrays.asList(
                new ResultMapping("name","姓名",new StringTypeHandler()),
                new ResultMapping("birthday","生日",new DateTypeHandler()),
                new ResultMapping("bool","bool",new BooleanTypeHandler()),
                new ResultMapping("age","年龄",new StringTypeHandler())
        ));
        List<Person> list = workbookParser.parse(workbook, 0, new ResultMapHandler<Person>(0, personResultMap));
        System.out.println();

    }

}