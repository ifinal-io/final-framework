package org.ifinal.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.ifinal.finalframework.util.format.DateFormatterPattern;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

    private final DateTimeFormatter formatter;

    public LocalDateSerializer() {
        this.formatter = DateTimeFormatter.ofPattern(DateFormatterPattern.YYYY_MM_DD.getPattern());
    }

    public LocalDateSerializer(final DateTimeFormatter formatter) {

        this.formatter = formatter;
    }

    @Override
    public void serialize(final LocalDate localDate, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

        if (localDate == null) {
            return;
        }
        if (formatter != null) {
            gen.writeString(localDate.format(formatter));
        } else {
            gen.writeNumber(localDate.toEpochDay());
        }
    }

}
