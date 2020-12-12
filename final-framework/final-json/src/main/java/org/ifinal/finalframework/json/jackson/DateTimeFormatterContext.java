package org.ifinal.finalframework.json.jackson;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DateTimeFormatterContext {
    private static final ThreadLocal<DateTimeFormatter> dateTimeFormatter = new ThreadLocal<>();
    private static final ThreadLocal<ZoneOffset> zoneOffset = new ThreadLocal<>();

    private DateTimeFormatterContext() {
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatterContext.dateTimeFormatter.get();
    }

    public static void setDateTimeFormatter(final DateTimeFormatter formatter) {

        dateTimeFormatter.set(formatter);
    }

    public static ZoneOffset getZoneOffset() {
        return DateTimeFormatterContext.zoneOffset.get();
    }

    public static void setZoneOffset(final ZoneOffset zoneOffset) {

        DateTimeFormatterContext.zoneOffset.set(zoneOffset);
    }


    public static void clear() {
        dateTimeFormatter.remove();
        zoneOffset.remove();
    }

}
