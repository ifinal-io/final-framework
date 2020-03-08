package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.formatter.DateFormatterPattern;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 13:05:25
 * @since 1.0
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();
    private static final int ZONE_OFFSET = TimeZone.getDefault().getRawOffset() / 3600 / 1000;
    private static final String ZONE_OFFSET_ID = ZONE_OFFSET > 0 ? "+" + ZONE_OFFSET : "-" + ZONE_OFFSET;

    private DateTimeFormatter formatter;

    public LocalDateSerializer() {
        this.formatter = DateTimeFormatter.ofPattern(DateFormatterPattern.YYYY_MM_DD.getPattern());
    }

    public LocalDateSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (localDate == null) return;
        if (formatter != null) {
            gen.writeString(localDate.format(formatter));
        } else {
            gen.writeNumber(localDate.toEpochDay());
        }
    }
}
