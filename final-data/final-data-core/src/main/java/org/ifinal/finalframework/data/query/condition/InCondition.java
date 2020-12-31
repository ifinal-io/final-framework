package org.ifinal.finalframework.data.query.condition;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("all")
public interface InCondition<V, R> extends Condition {

    default R in(@NonNull V... values) {
        return in(Arrays.asList(values));
    }

    R in(@NonNull Collection<V> values);

    default R nin(@NonNull V... values) {
        return in(Arrays.asList(values));
    }

    R nin(@NonNull Collection<V> values);

}
