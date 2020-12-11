package org.ifinal.finalframework.mybatis.sql.provider;


import lombok.Data;

import java.awt.Point;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class DistanceValue {
    private Point location;
    private Long distance;
}

