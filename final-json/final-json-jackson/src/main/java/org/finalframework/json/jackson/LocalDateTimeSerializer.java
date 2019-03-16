package org.finalframework.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 13:05:25
 * @since 1.0
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private static final int ZONE_OFFSET = TimeZone.getDefault().getRawOffset() / 3600 / 1000;
    private static final String ZONE_OFFSET_ID = ZONE_OFFSET > 0 ? "+" + ZONE_OFFSET : "-" + ZONE_OFFSET;

    public static void main(String[] args) {

        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of(ZONE_OFFSET_ID)).toEpochMilli());

    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (localDateTime == null) return;
        DateTimeFormatter formatter = DateTimeFormatterContext.getDateTimeFormatter();
        if (formatter != null) {
            gen.writeString(localDateTime.format(formatter));
        } else {
            gen.writeNumber(localDateTime.toInstant(ZoneOffset.of(ZONE_OFFSET_ID)).toEpochMilli());
        }
    }
}
