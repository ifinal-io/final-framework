package org.finalframework.annotation.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-06 23:58:57
 * @see Between
 * @see NotBetween
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BetweenValue<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 6194662646358531082L;
    T min;
    T max;
}

