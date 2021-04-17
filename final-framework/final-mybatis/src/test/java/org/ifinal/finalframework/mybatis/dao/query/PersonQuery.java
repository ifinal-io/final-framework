package org.ifinal.finalframework.mybatis.dao.query;

import org.ifinal.finalframework.core.IQuery;
import org.ifinal.finalframework.query.Direction;
import org.ifinal.finalframework.query.annotation.Limit;
import org.ifinal.finalframework.query.annotation.Order;

import lombok.Data;

/**
 * PersonQuery.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class PersonQuery implements IQuery {

    @Order(property = "name", order = 1)
    private Direction orderByName;

    @Order(property = "age", order = 2)
    private String orderByAge;

    @Limit
    private long limit;

}
