package org.finalframework.json.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.finalframework.auto.service.annotation.AutoService;
import org.finalframework.util.Asserts;
import org.finalframework.util.format.LocalDateTimeFormatters;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 16:59:46
 * @since 1.0
 */
@AutoService(JsonDeserializer.class)
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private LocalDateTimeFormatters dateTimeFormatters = LocalDateTimeFormatters.DEFAULT;

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if (Asserts.isEmpty(value)) {
            return null;
        }
        if (!p.isNaN()) {
            long timestamp = p.getValueAsLong();
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }

        return dateTimeFormatters.parse(value);

    }
}
