package org.ifinal.finalframework.util.format;

import lombok.Getter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public enum DateFormatterPattern {
    /**
     * yyyy-MM-dd HH:mm:ss.
     */
    YYYY_MM_DD_HH_MM_SS("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss"),
    /**
     * yyyy-MM-dd.
     */
    YYYY_MM_DD("^\\d{4}-\\d{2}-\\d{2}$", "yyyy-MM-dd"),
    /**
     * yyyyMMdd HH:mm:ss.
     */
    YYYYMMDD_HH_MM_SS("^\\d{8}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyyMMdd HH:mm:ss"),
    /**
     * yyyyMMdd.
     */
    YYYYMMDD("^\\d{8}$", "yyyyMMdd"),
    /**
     * yyyy/MM/dd HH:mm:ss.
     */
    YYYY__MM__DD_HH_MM_SS("^\\d{4}/\\d{2}/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss"),
    /**
     * yyyy/MM/dd.
     */
    YYYY__MM__DD("^\\d{4}/\\d{2}/\\d{2}$", "yyyy/MM/dd"),
    /**
     * yyyyMMddHHmmss.
     */
    YYYYMMDDHHMMSS("^\\d{14}$", "yyyyMMddHHmmss");

    @Getter
    private final String regex;

    @Getter
    private final String pattern;

    DateFormatterPattern(final String regex, final String pattern) {

        this.regex = regex;
        this.pattern = pattern;
    }
}
