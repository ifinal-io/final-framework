package org.finalframework.data.query.sql;


import lombok.Data;
import org.finalframework.annotation.query.AndOr;
import org.finalframework.annotation.query.Criteria;
import org.finalframework.annotation.query.Equal;
import org.finalframework.annotation.query.NotEqual;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 18:02:52
 * @since 1.0
 */
@Data
@Criteria(AndOr.OR)
public class MyQuery {
    @Equal
    private String name;
    @NotEqual
    private Integer age;
}

