package com.ilikly.finalframework.json.jackson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 21:03:06
 * @since 1.0
 */
public enum DateTimeFormatterPattern {
    YYYY_MM_DD_HH_MM_SS("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss"),
    YYYY__MM__DD_HH_MM_SS("^\\d{4}/\\d{2}/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss"),
    ;
    private static final Logger logger = LoggerFactory.getLogger(DateTimeFormatterPattern.class);

    private final String regex;
    private final String pattern;
    private final DateTimeFormatter dateTimeFormatter;

    DateTimeFormatterPattern(String regex, String pattern) {
        this.regex = regex;
        this.pattern = pattern;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    public boolean matches(String value) {
        boolean matches = value.matches(regex);
        logger.info("{} match pattern {} result={}", value, pattern, matches);
        return matches;
    }

    public LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, dateTimeFormatter);
    }

    public String format(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }


}
