package org.ifinal.finalframework.data.query.criterion.function;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface LogicFunction<V, R> {

    R and(@NonNull V value);

    R or(@NonNull V value);

    R xor(@NonNull V value);

    R not();

}
