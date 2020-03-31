package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.Assert;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.BetweenCriterion;
import org.finalframework.data.query.criterion.SimpleCriterion;
import org.finalframework.data.query.criterion.CriterionValue;
import org.finalframework.data.query.criterion.SingleCriterion;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 23:25:17
 * @since 1.0
 */
public class CriterionSerializer extends JsonSerializer<SimpleCriterion> {
    @Override
    public void serialize(SimpleCriterion simpleCriterion, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        if (simpleCriterion instanceof SingleCriterion) {
            serialize((SingleCriterion<?>) simpleCriterion, gen, serializers);
        } else if (simpleCriterion instanceof BetweenCriterion) {
            serialize((BetweenCriterion<?>) simpleCriterion, gen, serializers);
        }
        gen.writeEndObject();
    }

    private void serialize(SingleCriterion<?> criterion, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        CriterionValue<?> target = criterion.getTarget();
        Object value = criterion.getValue();
        if (target.getValue() instanceof QProperty) {
            gen.writeFieldName(((QProperty<?>) target.getValue()).getName());
        }
        if (Assert.isEmpty(target.getFunctions()) && Assert.isEmpty(criterion.getFunctions())) {
            gen.writeObject(value);
        }

    }

    private void serialize(BetweenCriterion<?> criterion, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }
}

