package org.finalframework.json.jackson.serializer.query;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.Assert;
import org.finalframework.data.query.Criteria;
import org.finalframework.data.query.Pageable;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.criterion.Criterion;

import java.io.IOException;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-06 23:25:17
 * @since 1.0
 */
public class QuerySerializer extends JsonSerializer<Query> {

    private final CriteriaJsonSerializer criteriaJsonSerializer = new CriteriaJsonSerializer();
    private final SortSerializer sortSerializer = new SortSerializer();
    private final GroupSerializer groupSerializer = new GroupSerializer();
    private final LimitSerializer limitSerializer = new LimitSerializer();

    @Override
    public void serialize(Query query, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        writePageable(query, gen, serializers);
        List<Criterion> criteria = query.getCriteria();
        if (Assert.nonEmpty(criteria)) {
            gen.writeFieldName("where");
            criteriaJsonSerializer.serialize(Criteria.where(criteria), gen, serializers);
        }
        sortSerializer.serialize(query.getSort(), gen, serializers);
        groupSerializer.serialize(query.getGroup(), gen, serializers);
        limitSerializer.serialize(query.getLimit(), gen, serializers);
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

