package org.finalframework.data.query.criterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @since 1.0
 */
public interface SingleCriterion<T> extends SimpleCriterion {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T getValue();

    interface Builder<T> extends SimpleCriterion.Builder<SingleCriterion<T>, Builder<T>> {

        Builder<T> value(T value);
    }

}
