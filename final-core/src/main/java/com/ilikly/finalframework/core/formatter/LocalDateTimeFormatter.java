package com.ilikly.finalframework.core.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 22:30:15
 * @since 1.0
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    public static final LocalDateTimeFormatter YYYY_MM_DD_HH_MM_SS = new LocalDateTimeFormatter(DateFormatterPattern.YYYY_MM_DD_HH_MM_SS);
    public static final LocalDateTimeFormatter YYYY__MM__DD_HH_MM_SS = new LocalDateTimeFormatter(DateFormatterPattern.YYYY__MM__DD_HH_MM_SS);
    @SuppressWarnings("all")
    public static final LocalDateTimeFormatter YYYYMMDDHHMMSS = new LocalDateTimeFormatter(DateFormatterPattern.YYYYMMDDHHMMSS);
    @SuppressWarnings("all")
    public static final LocalDateTimeFormatter YYYYMMDD_HH_MM_SS = new LocalDateTimeFormatter(DateFormatterPattern.YYYYMMDD_HH_MM_SS);

    private final String regex;
    private final DateTimeFormatter dateTimeFormatter;

    public LocalDateTimeFormatter(String regex, String pattern) {
        this.regex = regex;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    public LocalDateTimeFormatter(DateFormatterPattern pattern) {
        this(pattern.getRegex(), pattern.getPattern());
    }

    @Override
    public LocalDateTime parse(String source) {
        return LocalDateTime.parse(source, dateTimeFormatter);
    }

    @Override
    public String format(LocalDateTime target) {
        return target.format(dateTimeFormatter);
    }

    @Override
    public boolean matches(String source) {
        return source.matches(regex);
    }
}
