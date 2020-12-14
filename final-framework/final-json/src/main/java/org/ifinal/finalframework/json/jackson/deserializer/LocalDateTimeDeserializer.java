package org.ifinal.finalframework.json.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.ifinal.finalframework.auto.service.annotation.AutoService;
import org.ifinal.finalframework.util.Asserts;
import org.ifinal.finalframework.util.format.LocalDateTimeFormatters;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(JsonDeserializer.class)
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final LocalDateTimeFormatters dateTimeFormatters = LocalDateTimeFormatters.DEFAULT;

    @Override
    public LocalDateTime deserialize(final JsonParser p, final DeserializationContext context) throws IOException {

        final String value = p.getValueAsString();
        if (Asserts.isEmpty(value)) {
            return null;
        }
        if (!p.isNaN()) {
            final long timestamp = p.getValueAsLong();
            final Instant instant = Instant.ofEpochMilli(timestamp);
            final ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }

        return dateTimeFormatters.parse(value);

    }

}
