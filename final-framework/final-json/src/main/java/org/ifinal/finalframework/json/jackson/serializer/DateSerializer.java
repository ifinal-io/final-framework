package org.ifinal.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.ifinal.finalframework.util.format.DateFormatterPattern;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateSerializer extends JsonSerializer<Date> {

    public static final DateSerializer INSTANCE = new DateSerializer();

    private final String pattern;

    public DateSerializer(final String pattern) {

        this.pattern = pattern;
    }

    public DateSerializer() {
        this(DateFormatterPattern.YYYY_MM_DD_HH_MM_SS.getPattern());
    }

    @Override
    public void serialize(final Date date, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

        if (date == null) {
            return;
        }
        if (pattern != null) {
            gen.writeString(new SimpleDateFormat(pattern).format(date));
        } else {
            gen.writeNumber(date.getTime());
        }
    }

}
