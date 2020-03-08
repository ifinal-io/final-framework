package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.Assert;
import org.finalframework.data.query.*;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.ICriterion;

import java.io.IOException;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 23:25:17
 * @since 1.0
 */
public class LimitSerializer extends JsonSerializer<Limit> {
    @Override
    public void serialize(Limit limit, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (limit == null) return;
        if (limit.getOffset() != null && limit.getLimit() != null) {
            gen.writeNumberField("offset", limit.getOffset());
            gen.writeNumberField("limit", limit.getLimit());
        } else if (limit.getLimit() != null) {
            gen.writeNumberField("limit", limit.getLimit());
        }
    }
}

