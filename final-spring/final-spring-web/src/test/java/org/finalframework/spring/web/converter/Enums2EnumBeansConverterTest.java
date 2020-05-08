package org.finalframework.spring.web.converter;

import org.finalframework.data.entity.enums.YN;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-08 12:55:18
 * @since 1.0
 */
class Enums2EnumBeansConverterTest {

    private Enums2EnumBeansConverter enums2EnumBeansConverter = new Enums2EnumBeansConverter();

    @Test
    public void testEnumClassConverter() {
        assertTrue(enums2EnumBeansConverter.matches(YN.class));
        final List<Map<String, Object>> maps = enums2EnumBeansConverter.convert(YN.class);
        assertTrue(maps.size() == 3);
    }

}