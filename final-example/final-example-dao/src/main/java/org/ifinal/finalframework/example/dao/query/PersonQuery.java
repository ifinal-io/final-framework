package org.ifinal.finalframework.example.dao.query;

import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.query.Equal;
import org.ifinal.finalframework.annotation.query.JsonContains;
import org.ifinal.finalframework.annotation.query.PageQuery;
import org.ifinal.finalframework.annotation.query.function.JsonExtract;
import org.ifinal.finalframework.annotation.query.function.Max;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class PersonQuery extends PageQuery {

    @Equal
    @Max("age")
    private Integer age;

    @JsonExtract(path = "$.key")
    @JsonContains(property = "map")
    private String value;

}
