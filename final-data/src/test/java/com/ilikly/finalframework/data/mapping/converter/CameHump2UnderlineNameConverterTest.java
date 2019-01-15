package com.ilikly.finalframework.data.mapping.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-14 23:23:46
 * @since 1.0
 */
public class CameHump2UnderlineNameConverterTest {

    private final NameConverter nameConverter = new CameHump2UnderlineNameConverter();

    @Test
    public void map() {
        assertEquals("tcp", nameConverter.map("TCP"));
        assertEquals("first_name", nameConverter.map("firstName"));
        assertEquals("my_tcp", nameConverter.map("myTCP"));
    }
}