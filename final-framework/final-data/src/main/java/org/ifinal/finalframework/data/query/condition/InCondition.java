package org.ifinal.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("all")
public interface InCondition<V, R> extends Condition {

    R in(@NonNull Collection<?> values);

    R nin(@NonNull Collection<?> values);

}
