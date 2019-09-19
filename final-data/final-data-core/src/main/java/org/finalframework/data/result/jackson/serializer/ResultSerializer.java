package org.finalframework.data.result.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.data.result.Result;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-06 17:25:06
 * @since 1.0
 */
public class ResultSerializer extends JsonSerializer<Result> {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void serialize(Result value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        writeObjectField("success", value.isSuccess(), gen, null);
        writeObjectField("status", value.getStatus(), gen, null);
        writeObjectField("message", value.getMessage(), gen, null);
        writeObjectField("toast", value.getToast(), gen, null);
        writeObjectField("view", value.getView(), gen, null);
        writeObjectField("data", value.getData(), gen, value.getView());
        writeObjectField("trace", value.getTrace(), gen, null);
        writeObjectField("timestamp", value.getTimestamp(), gen, null);
        gen.writeEndObject();
    }

    private void writeObjectField(String name, Object value, JsonGenerator gen, Class view) throws IOException {
        if (value == null) return;
        if (view != null) {
            gen.writeFieldName(name);
            ObjectWriter objectWriter = objectMapper.writerWithView(view);
            objectWriter.writeValue(gen, value);
        } else {
            gen.writeObjectField(name, value);
        }
    }

}
