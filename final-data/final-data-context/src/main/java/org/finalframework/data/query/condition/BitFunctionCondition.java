package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 13:21:41
 * @since 1.0
 */
public interface BitFunctionCondition<V, R> extends FunctionCondition {

    R and(@NonNull V value);

    R or(@NonNull V value);

    R xor(@NonNull V value);

    R not();
}
