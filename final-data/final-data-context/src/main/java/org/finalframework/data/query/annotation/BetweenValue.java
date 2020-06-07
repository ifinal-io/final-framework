package org.finalframework.data.query.annotation;


import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-06 23:58:57
 * @since 1.0
 */
@Data
public class BetweenValue<T extends Serializable> {
    T min;
    T max;
}

