/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.mybatis.sql.provider;


import lombok.Data;
import org.finalframework.data.annotation.query.*;

import java.awt.*;
import java.util.List;

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
    @EQUAL
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
    @LESS_THAN(property = "name")
    private Long distance;

//    @Offset
//    private Integer offset;
//    @Limit
//    private Integer limit;
}

