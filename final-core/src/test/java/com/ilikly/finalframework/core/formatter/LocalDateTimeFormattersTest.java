package com.ilikly.finalframework.core.formatter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 22:36:40
 * @since 1.0
 */
public class LocalDateTimeFormattersTest {
    private LocalDateTimeFormatters dateFormatters = LocalDateTimeFormatters.DEFAULT;

    @Test
    @SuppressWarnings("all")
    public void parse() {

        String YYYY_MM_DD_HH_MM_SS = "2019-02-14 12:13:14";
        assertEquals(YYYY_MM_DD_HH_MM_SS, LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS.format(dateFormatters.parse(YYYY_MM_DD_HH_MM_SS)));

        String YYYY__MM__DD_HH_MM_SS = "2019/02/14 12:13:14";
        assertEquals(YYYY__MM__DD_HH_MM_SS, LocalDateTimeFormatter.YYYY__MM__DD_HH_MM_SS.format(dateFormatters.parse(YYYY__MM__DD_HH_MM_SS)));

        String YYYYMMDD_HH_MM_SS = "20190214 12:13:14";
        assertEquals(YYYYMMDD_HH_MM_SS, LocalDateTimeFormatter.YYYYMMDD_HH_MM_SS.format(dateFormatters.parse(YYYYMMDD_HH_MM_SS)));

        String YYYYMMDDHHMMSS = "20190214121314";
        assertEquals(YYYYMMDDHHMMSS, LocalDateTimeFormatter.YYYYMMDDHHMMSS.format(dateFormatters.parse(YYYYMMDDHHMMSS)));
    }
}