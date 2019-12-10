package org.finalframework.json.jackson.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.data.view.JsonViewValue;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 10:33:49
 * @since 1.0
 */
public class JsonViewValueSerializer extends JsonSerializer<JsonViewValue> {
    private final ObjectMapper objectMapper;

    public JsonViewValueSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void serialize(JsonViewValue value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        serializers.getConfig().withView(value.getView());
        ObjectWriter objectWriter = objectMapper.writerWithView(value.getView());
        objectWriter.writeValue(gen, value.getValue());
    }
}

