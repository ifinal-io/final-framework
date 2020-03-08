package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.data.query.Group;
import org.finalframework.data.query.Order;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.Sort;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 23:25:17
 * @since 1.0
 */
public class SortJsonSerializer extends JsonSerializer<Sort> {
    @Override
    public void serialize(Sort sort, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (sort == null) return;
        gen.writeFieldName("order");
        gen.writeStartArray();
        for (Order order : sort) {
            gen.writeStartObject();
            gen.writeStringField(order.getProperty().getName(), order.getDirection().name());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}

