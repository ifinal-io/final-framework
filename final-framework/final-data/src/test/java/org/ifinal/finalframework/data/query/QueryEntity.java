package org.ifinal.finalframework.data.query;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.data.AbsEntity;
import org.ifinal.finalframework.annotation.data.Geometry;
import org.ifinal.finalframework.annotation.data.Json;
import org.springframework.data.geo.Point;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

