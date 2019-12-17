package org.finalframework.data.query.criterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @see SingleCriterionOperation
 * @since 1.0
 */
public interface SingleCriterion<T> extends Criterion {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T getValue();

    interface Builder<T> extends Criterion.Builder<SingleCriterion<T>, Builder<T>> {

        Builder<T> value(T value);
    }

}
