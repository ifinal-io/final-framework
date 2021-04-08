package org.ifinal.finalframework.mybatis.sql.provider;

import org.ifinal.finalframework.query.Direction;
import org.ifinal.finalframework.query.annotation.Equal;
import org.ifinal.finalframework.query.annotation.LessThan;
import org.ifinal.finalframework.query.annotation.Limit;
import org.ifinal.finalframework.query.annotation.Offset;
import org.ifinal.finalframework.query.annotation.Order;

import java.awt.Point;
import lombok.Data;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class PersonQuery {

    @Equal
    private String name;

    private Point location;

    @LessThan(property = "name")
    private Long distance;

    @Offset
    private Integer offset;

    @Limit
    private Integer limit;

    @Order(property = "name", order = 1)
    private Direction orderByName = Direction.DESC;

    @Order(property = "age", order = 2)
    private String orderByAge = "ASC";

}

