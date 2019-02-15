package com.ilikly.finalframework.core.formatter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 22:14:55
 * @since 1.0
 */
public class DateFormattersTest {

    private DateFormatters dateFormatters = DateFormatters.DEFAULT;

    @Test
    @SuppressWarnings("all")
    public void parse() {
        String YYYY_MM_DD_HH_MM_SS = "2019-02-14 12:13:14";
        assertEquals(YYYY_MM_DD_HH_MM_SS, DateFormatter.YYYY_MM_DD_HH_MM_SS.format(dateFormatters.parse(YYYY_MM_DD_HH_MM_SS)));
        String YYYY_MM_DD = "2019-02-14";
        assertEquals(YYYY_MM_DD, DateFormatter.YYYY_MM_DD.format(dateFormatters.parse(YYYY_MM_DD)));

        String YYYY__MM__DD_HH_MM_SS = "2019/02/14 12:13:14";
        assertEquals(YYYY__MM__DD_HH_MM_SS, DateFormatter.YYYY__MM__DD_HH_MM_SS.format(dateFormatters.parse(YYYY__MM__DD_HH_MM_SS)));
        String YYYY__MM__DD = "2019/02/14";
        assertEquals(YYYY__MM__DD, DateFormatter.YYYY__MM__DD.format(dateFormatters.parse(YYYY__MM__DD)));

        String YYYYMMDD_HH_MM_SS = "20190214 12:13:14";
        assertEquals(YYYYMMDD_HH_MM_SS, DateFormatter.YYYYMMDD_HH_MM_SS.format(dateFormatters.parse(YYYYMMDD_HH_MM_SS)));
        String YYYYMMDD = "20190214";
        assertEquals(YYYYMMDD, DateFormatter.YYYYMMDD.format(dateFormatters.parse(YYYYMMDD)));

        String YYYYMMDDHHMMSS = "20190214121314";
        assertEquals(YYYYMMDDHHMMSS, DateFormatter.YYYYMMDDHHMMSS.format(dateFormatters.parse(YYYYMMDDHHMMSS)));
    }
}