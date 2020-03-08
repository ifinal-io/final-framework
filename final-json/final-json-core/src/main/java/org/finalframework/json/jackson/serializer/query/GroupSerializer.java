package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.data.query.Group;
import org.finalframework.data.query.Limit;
import org.finalframework.data.query.QProperty;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 23:25:17
 * @since 1.0
 */
public class GroupSerializer extends JsonSerializer<Group> {
    @Override
    public void serialize(Group group, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (group == null) return;
        gen.writeFieldName("group");
        gen.writeStartArray();
        for (QProperty<?> property : group) {
            gen.writeString(property.getName());
        }
        gen.writeEndArray();
    }
}

