package org.finalframework.data.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.annotation.data.Json;
import org.finalframework.data.mapping.Property;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 18:03:19
 * @since 1.0
 */
public class PropertyJsonSerializer extends JsonSerializer<Property> {
    @Override
    public void serialize(Property property, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("name", property.getName());
        gen.writeStringField("column", property.getColumn());
        gen.writeStringField("type", property.getType().getCanonicalName());
//        gen.writeStringField("javaType", property.getJavaType().getCanonicalName());

        if (property.isMap()) {
            gen.writeStringField("keyType", property.getComponentType().getCanonicalName());
            gen.writeStringField("valueType", property.getMapValueType().getCanonicalName());
        } else if (property.isCollectionLike()) {
            gen.writeStringField("elementType", property.getComponentType().getCanonicalName());
        }

        gen.writeFieldName("properties");
        gen.writeStartObject();

        gen.writeBooleanField("isIdProperty", property.isIdProperty());
        gen.writeBooleanField("isEnum", property.isEnum());
        gen.writeBooleanField("isReference", property.isReference());
        gen.writeBooleanField("isJson", property.isAnnotationPresent(Json.class));
        gen.writeBooleanField("isTransient", property.isTransient());
        gen.writeBooleanField("isVirtual", property.isVirtual());
        gen.writeBooleanField("isReadOnly", property.isReadOnly());
        gen.writeBooleanField("isWriteOnly", property.isWriteOnly());

        gen.writeEndObject();


        gen.writeEndObject();
    }
}

