package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.Query;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-08 20:24:46
 * @since 1.0
 */
public class QueryDeserializer extends JsonDeserializer<Query> {
    QEntity entity;

    @Override
    public Query deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Query query = new Query();
        return query;
    }
}

