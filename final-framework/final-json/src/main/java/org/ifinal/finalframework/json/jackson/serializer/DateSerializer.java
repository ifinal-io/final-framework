package org.ifinal.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ifinal.finalframework.util.format.DateFormatterPattern;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateSerializer extends JsonSerializer<Date> {

    public static final DateSerializer INSTANCE = new DateSerializer();

    private final String pattern;

    public DateSerializer(String pattern) {
        this.pattern = pattern;
    }

    public DateSerializer() {
        this(DateFormatterPattern.YYYY_MM_DD_HH_MM_SS.getPattern());
    }

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (date == null) return;
        if (pattern != null) {
            gen.writeString(new SimpleDateFormat(pattern).format(date));
        } else {
            gen.writeNumber(date.getTime());
        }
    }
}
