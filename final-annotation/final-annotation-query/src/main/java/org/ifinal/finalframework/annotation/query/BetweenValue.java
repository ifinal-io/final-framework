package org.ifinal.finalframework.annotation.query;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author likly
 * @version 1.0.0
 * @see Between
 * @see NotBetween
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BetweenValue<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 6194662646358531082L;

    T min;

    T max;

}

