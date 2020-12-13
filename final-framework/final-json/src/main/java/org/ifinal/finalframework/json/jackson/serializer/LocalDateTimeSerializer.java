package org.ifinal.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.ifinal.finalframework.auto.service.annotation.AutoService;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(JsonSerializer.class)
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private DateTimeFormatter formatter;

    public LocalDateTimeSerializer() {
    }

    public LocalDateTimeSerializer(final DateTimeFormatter formatter) {

        this.formatter = formatter;
    }

    @Override
    public void serialize(final LocalDateTime localDateTime, final JsonGenerator gen,
        final SerializerProvider serializers) throws IOException {

        if (localDateTime == null) {
            return;
        }
        if (formatter != null) {
            gen.writeString(localDateTime.format(formatter));
        } else {
            gen.writeNumber(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
    }

}
