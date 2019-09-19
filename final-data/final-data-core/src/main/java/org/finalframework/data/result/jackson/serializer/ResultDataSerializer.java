package org.finalframework.data.result.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-26 18:20:38
 * @since 1.0
 */
public class ResultDataSerializer extends JsonSerializer<Object> {

    private List<DataSerializer<Object>> dataSerializers = new ArrayList<>();

    {
        dataSerializers.add(new EnumsDataSerializer());
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        for (DataSerializer serializer : dataSerializers) {
            if (serializer.supports(value)) {
                serializer.serialize(value, gen, serializers);
                return;
            }
        }


        gen.writeObject(value);

    }
}
