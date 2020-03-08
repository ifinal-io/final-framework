package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.Assert;
import org.finalframework.data.query.Criteria;
import org.finalframework.data.query.Pageable;
import org.finalframework.data.query.Query;
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
public class QueryJsonSerializer extends JsonSerializer<Query> {

    private final CriteriaJsonSerializer criteriaJsonSerializer = new CriteriaJsonSerializer();
    private final SortJsonSerializer sortJsonSerializer = new SortJsonSerializer();
    private final GroupJsonSerializer groupJsonSerializer = new GroupJsonSerializer();
    private final LimitJsonSerializer limitJsonSerializer = new LimitJsonSerializer();

    @Override
    public void serialize(Query query, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        writePageable(query, gen, serializers);
        List<ICriterion> criteria = query.getCriteria();
        if (Assert.nonEmpty(criteria)) {
            gen.writeFieldName("where");
            criteriaJsonSerializer.serialize(Criteria.where(criteria), gen, serializers);
        }
        sortJsonSerializer.serialize(query.getSort(), gen, serializers);
        groupJsonSerializer.serialize(query.getGroup(), gen, serializers);
        limitJsonSerializer.serialize(query.getLimit(), gen, serializers);
        gen.writeEndObject();
    }


    private void writePageable(Pageable pageable, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (pageable == null) return;
        if (pageable.getPage() != null) {
            gen.writeNumberField("page", pageable.getPage());
        }

        if (pageable.getSize() != null) {
            gen.writeNumberField("size", pageable.getSize());
        }
    }


}

