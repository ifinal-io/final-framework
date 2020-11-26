package org.ifinal.finalframework.json.jackson;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateTimeFormatterContext {
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ThreadLocal<DateTimeFormatter> dateTimeFormatter = new ThreadLocal<>();
    private static final ThreadLocal<ZoneOffset> zoneOffset = new ThreadLocal<>();

    public static DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatterContext.dateTimeFormatter.get();
//        return dateTimeFormatter == null ? DEFAULT_DATE_FORMATTER : dateTimeFormatter;
        return dateTimeFormatter;
    }

    public static void setDateTimeFormatter(DateTimeFormatter formatter) {
        dateTimeFormatter.set(formatter);
    }

    public static ZoneOffset getZoneOffset() {
        return DateTimeFormatterContext.zoneOffset.get();
    }

    public static void setZoneOffset(ZoneOffset zoneOffset) {
        DateTimeFormatterContext.zoneOffset.set(zoneOffset);
    }

}
