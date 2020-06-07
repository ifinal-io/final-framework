package org.finalframework.data.query;


import lombok.Data;
import org.finalframework.data.query.annotation.Equal;
import org.finalframework.data.query.annotation.Or;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-07 00:15:52
 * @since 1.0
 */
@Data
@Or
public class QueryEntityQuery {
    @Equal
    private String name;
    @Equal
    private Integer age;
}

