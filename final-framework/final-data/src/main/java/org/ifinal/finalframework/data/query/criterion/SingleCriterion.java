package org.ifinal.finalframework.data.query.criterion;

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
    default void apply(StringBuilder parent, String expression) {

    }

    interface Builder<T> extends SimpleCriterion.Builder<SingleCriterion<T>, Builder<T>> {

        Builder<T> value(T value);
    }

}
