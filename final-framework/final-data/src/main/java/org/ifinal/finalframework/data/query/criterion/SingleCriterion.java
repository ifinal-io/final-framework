package org.ifinal.finalframework.data.query.criterion;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SingleCriterion<T> extends SimpleCriterion {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T getValue();

    @Override
    default void apply(@NonNull StringBuilder parent, @NonNull String expression) {
        // nothing
    }

    interface Builder<T> extends SimpleCriterion.Builder<SingleCriterion<T>, Builder<T>> {

        Builder<T> value(T value);
    }

}
