package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.Assert;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.*;
import org.finalframework.data.query.criterion.operator.CriterionOperator;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 23:25:17
 * @since 1.0
 */
public class CriterionJsonSerializer extends JsonSerializer<Criterion> {
    @Override
    public void serialize(Criterion criterion, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        if (criterion instanceof SingleCriterion) {
            serialize((SingleCriterion<?>) criterion, gen, serializers);
        } else if (criterion instanceof BetweenCriterion) {
            serialize((BetweenCriterion<?>) criterion, gen, serializers);
        }
        gen.writeEndObject();
    }

    private void serialize(SingleCriterion<?> criterion, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        switch (criterion.getOperator().getName()) {
            case "IN":
            case "NOT_IN":
                break;
            case "EQUAL":

                CriterionValue<?> target = criterion.getTarget();
                Object value = criterion.getValue();
                if (target.getValue() instanceof QProperty) {
                    gen.writeFieldName(((QProperty<?>) target.getValue()).getName());
                }
                if (Assert.isEmpty(target.getFunctions()) && Assert.isEmpty(criterion.getFunctions())) {
                    gen.writeObject(value);
                }

                break;
        }
    }

    private void serialize(BetweenCriterion<?> criterion, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }
}

