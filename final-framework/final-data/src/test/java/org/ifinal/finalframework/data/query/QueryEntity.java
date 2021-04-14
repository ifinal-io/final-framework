package org.ifinal.finalframework.data.query;

import org.springframework.data.geo.Point;

import org.ifinal.finalframework.data.annotation.AbsEntity;
import org.ifinal.finalframework.data.annotation.Geometry;
import org.ifinal.finalframework.data.annotation.Json;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

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

