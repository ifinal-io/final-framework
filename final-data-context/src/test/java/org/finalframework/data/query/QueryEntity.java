

package org.finalframework.data.query;


import lombok.Getter;
import lombok.Setter;
import org.finalframework.annotation.data.AbsEntity;
import org.finalframework.annotation.data.Geometry;
import org.finalframework.annotation.data.Json;
import org.springframework.data.geo.Point;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 23:06:16
 * @since 1.0
 */
@Setter
@Getter
public class QueryEntity extends AbsEntity {

    private String name;
    private Integer age;
    @Json
    private List<Integer> intList;

    @Geometry
    private Point point;
}

