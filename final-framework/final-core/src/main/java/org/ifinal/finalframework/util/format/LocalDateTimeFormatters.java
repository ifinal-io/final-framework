package org.ifinal.finalframework.util.format;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.ifinal.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LocalDateTimeFormatters implements Formatters<LocalDateTime> {

    public static final LocalDateTimeFormatters DEFAULT = new LocalDateTimeFormatters();

    private final List<LocalDateTimeFormatter> dateFormatters = new ArrayList<>();

    public LocalDateTimeFormatters(final List<LocalDateTimeFormatter> dateFormatters) {

        this.dateFormatters.addAll(dateFormatters);
    }

    public LocalDateTimeFormatters() {
        this(Arrays.asList(
            LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS,
            LocalDateTimeFormatter.YYYY2_MM2_DD_HH_MM_SS,
            LocalDateTimeFormatter.YYYYMMDD_HH_MM_SS,
            LocalDateTimeFormatter.YYYYMMDDHHMMSS
        ));
    }

    @Override
    public LocalDateTime parse(final String source) {

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
