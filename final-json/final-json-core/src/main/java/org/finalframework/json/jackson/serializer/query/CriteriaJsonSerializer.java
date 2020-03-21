package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.Assert;
import org.finalframework.data.query.Criteria;
import org.finalframework.data.query.criterion.SimpleCriterion;
import org.finalframework.data.query.criterion.Criterion;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 23:25:17
 * @since 1.0
 */
public class CriteriaJsonSerializer extends JsonSerializer<Criteria> {

    private final CriterionSerializer criterionSerializer = new CriterionSerializer();

    @Override
    public void serialize(Criteria criteria, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Assert.nonEmpty(criteria)) {
            switch (criteria.andOr()) {
                case AND:
                    gen.writeStartArray();
                    for (Criterion criterion : criteria) {
                        if (criterion.isChain()) {
                            serialize((Criteria) criterion, gen, serializers);
                        } else {
                            criterionSerializer.serialize((SimpleCriterion) criterion, gen, serializers);
                        }
                    }
                    gen.writeEndArray();
                    break;
                case OR:
                    gen.writeStartObject();
                    gen.writeFieldName(criteria.andOr().name());
                    gen.writeStartArray();
                    for (Criterion criterion : criteria) {
                        if (criterion.isChain()) {
                            serialize((Criteria) criterion, gen, serializers);
                        } else {
                            criterionSerializer.serialize((SimpleCriterion) criterion, gen, serializers);
                        }
                    }
                    gen.writeEndArray();
                    gen.writeEndObject();
                    break;
            }

        }
    }
}

