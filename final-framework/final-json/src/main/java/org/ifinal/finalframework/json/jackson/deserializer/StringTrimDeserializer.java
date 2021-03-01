package org.ifinal.finalframework.json.jackson.deserializer;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

/**
 * StringTrimDeserializer.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringTrimDeserializer extends StringDeserializer {

    @Override
    public String deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        String value = super.deserialize(p, ctxt);

        if (Objects.nonNull(value)) {
            return value.trim();
        }

        return value;
    }

}
