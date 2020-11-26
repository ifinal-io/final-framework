package org.ifinal.finalframework.json.jackson.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ifinal.finalframework.json.jackson.view.JsonViewValue;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonViewValueSerializer extends JsonSerializer<JsonViewValue> {
    private final ObjectMapper objectMapper;

    public JsonViewValueSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void serialize(JsonViewValue value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getValue());
//        SerializationConfig serializationConfig = serializers.getConfig().withView(value.getView());
//        ObjectWriter objectWriter = objectMapper.writerWithView(value.getView());
//        objectWriter.writeValue(gen, value.getValue());
    }
}

