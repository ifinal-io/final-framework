package org.finalframework.core.formatter;

import org.finalframework.core.Asserts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 22:35:18
 * @since 1.0
 */
public class LocalDateTimeFormatters implements Formatters<LocalDateTime> {
    public static final LocalDateTimeFormatters DEFAULT = new LocalDateTimeFormatters();
    private final List<LocalDateTimeFormatter> dateFormatters = new ArrayList<>();

    public LocalDateTimeFormatters(List<LocalDateTimeFormatter> dateFormatters) {
        this.dateFormatters.addAll(dateFormatters);
    }

    public LocalDateTimeFormatters() {
        this(Arrays.asList(
                LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS,
                LocalDateTimeFormatter.YYYY__MM__DD_HH_MM_SS,
                LocalDateTimeFormatter.YYYYMMDD_HH_MM_SS,
                LocalDateTimeFormatter.YYYYMMDDHHMMSS
        ));
    }

    @Override
    public LocalDateTime parse(String source) {
        if (Asserts.isEmpty(source)) {
            return null;
        }
        for (LocalDateTimeFormatter formatter : dateFormatters) {
            if (formatter.matches(source)) {
                return formatter.parse(source);
            }
        }
        return null;
    }
}
