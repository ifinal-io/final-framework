package org.finalframework.mybatis.sql.provider;


import lombok.Data;
import org.finalframework.annotation.query.Equal;
import org.finalframework.annotation.query.Function;
import org.finalframework.annotation.query.LessThan;

import java.awt.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 22:33:00
 * @since 1.0
 */
@Data
public class PersonQuery {
    //    @BETWEEN(property = "age")
//    BetweenValue<Integer> ageBetween;
//    @NOT_BETWEEN(property = "age")
//    BetweenValue<Integer> ageNotBetween;
    @Function("DATE(${column},4)")
    @Equal
    private String name;
//    @NOT_EQUAL
//    private Integer age;
//    @IS_NOT_NULL(property = "name")
//    private String nameIsNotNull;
//    @IS_NULL(property = "name")
//    private String nameIsNull;
//    @IN(property = "age")
//    private List<Integer> ages;
//    @JSON_CONTAINS(property = "name", attributes = @Attribute(name = "path", value = "$.name"))
//    private String jsonContains;

//    @DistanceIn(property = "name")
//    @LESS_THAN(property = "name",value = {
//            "<if test=\"${value} != null and ${value}.location != null and ${value}.distance != null\">",
//            "   <![CDATA[ST_Distance(${column},ST_GeomFromText(#{${value}.location})) &lt; #{${value}.distance}]]>",
//            "</if>"
//    })
//    private DistanceValue distance;


    private Point location;
    @Function("ST_Distance(${column},ST_GeomFromText(#{${query}.location}))")
    @LessThan(property = "name")
    private Long distance;

//    @Offset
//    private Integer offset;
//    @Limit
//    private Integer limit;
}

