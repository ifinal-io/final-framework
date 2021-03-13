package org.ifinal.finalframework.mybatis.jackson.serializer;

import org.ifinal.finalframework.auto.annotation.AutoService;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(JsonSerializer.class)
public class BoundSqlJsonSerializer extends JsonSerializer<BoundSql> {

    @Override
    public void serialize(final BoundSql value, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

        gen.writeStartObject();

        gen.writeStringField("sql", value.getSql());

        gen.writeFieldName("parameterMappings");
        gen.writeStartArray();
        for (ParameterMapping parameterMapping : value.getParameterMappings()) {
            gen.writeStartObject();
            gen.writeStringField("property", parameterMapping.getProperty());
            gen.writeObjectField("javaType", parameterMapping.getJavaType());
            gen.writeObjectField("typeHandler", parameterMapping.getTypeHandler());
            gen.writeStringField("expression", parameterMapping.getExpression());
            gen.writeEndObject();
        }

        gen.writeEndArray();

        gen.writeEndObject();
    }

}
