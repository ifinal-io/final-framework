package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.finalframework.core.Assert;
import org.finalframework.core.formatter.LocalDateTimeFormatters;

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
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private LocalDateTimeFormatters dateTimeFormatters = LocalDateTimeFormatters.DEFAULT;

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if (Assert.isEmpty(value)) return null;
        if (!p.isNaN()) {
            long timestamp = p.getValueAsLong();
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }

        LocalDateTime localDateTime = dateTimeFormatters.parse(value);
        if (localDateTime != null) return localDateTime;

        return null;

    }
}
