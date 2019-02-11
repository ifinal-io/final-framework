package com.ilikly.finalframework.json.jackson;

import java.time.LocalDateTime;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 21:14:42
 * @since 1.0
 */
public class DateTimeFormatters {

    public static void main(String[] args) {
        DateTimeFormatters dateTimeFormatters = new DateTimeFormatters();
        LocalDateTime localDateTime = dateTimeFormatters.parse("2018-09-12 12:50:30");
        System.out.println(DateTimeFormatterPattern.YYYY_MM_DD_HH_MM_SS.format(localDateTime));
    }

    public LocalDateTime parse(String value) {
        for (DateTimeFormatterPattern pattern : DateTimeFormatterPattern.values()) {
            if (pattern.matches(value)) {
                return pattern.parse(value);
            }
        }
        return null;
    }

}
