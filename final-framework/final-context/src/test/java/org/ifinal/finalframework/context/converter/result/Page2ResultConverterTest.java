package org.ifinal.finalframework.context.converter.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.ifinal.finalframework.annotation.core.result.Result;
import org.ifinal.finalframework.json.Json;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Page2ResultConverterTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class Page2ResultConverterTest {

    private final Page2ResultConverter<String> converter = new Page2ResultConverter<>();

    @Test
    void convert() {

        Page<String> page = new Page<>(1, 20);
        page.setTotal(100);
        page.addAll(Arrays.asList("A", "B", "C"));

        logger.info("page={}", Json.toJson(page));
        Result<ArrayList<String>> result = converter.convert(page);
        logger.info("result={}", Json.toJson(result));

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPagination());
        assertNotNull(result.getData());
        assertEquals(1, result.getPagination().getPage());
        assertEquals(20, result.getPagination().getSize());
        assertEquals(100, result.getPagination().getTotal());
        assertEquals(5, result.getPagination().getPages());
        assertEquals(true, result.getPagination().getFirstPage());
        assertEquals(false, result.getPagination().getLastPage());
    }

}
