package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @since 1.0
 */
public interface SingleCriterion<T> extends Criterion<T> {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T value();

    interface Builder<T> extends Criterion.Builder<SingleCriterion<T>> {

        @Override
        Builder<T> property(QProperty property);

        @Override
        Builder<T> operator(CriterionOperator operation);


        Builder<T> value(T value);
    }

}
