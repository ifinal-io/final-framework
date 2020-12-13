package org.ifinal.finalframework.mybatis.sql.provider;

import java.awt.Point;
import lombok.Data;
import org.ifinal.finalframework.annotation.query.Equal;
import org.ifinal.finalframework.annotation.query.Function;
import org.ifinal.finalframework.annotation.query.LessThan;
import org.ifinal.finalframework.annotation.query.Limit;
import org.ifinal.finalframework.annotation.query.Offset;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class PersonQuery {

    @Function("DATE(${column},4)")
    @Equal
    private String name;

    private Point location;

    @Function("ST_Distance(${column},ST_GeomFromText(#{${query}.location}))")
    @LessThan(property = "name")
    private Long distance;

    @Offset
    private Integer offset;

    @Limit
    private Integer limit;

}

