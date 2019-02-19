package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:52:10
 * @since 1.0
 */
public interface BetweenCriterion<T extends Comparable<T>> extends Criterion<T> {

    static <T extends Comparable<T>> Builder<T> builder() {
        return BetweenCriterionImpl.builder();
    }

    T min();

    T max();

    interface Builder<T extends Comparable<T>> extends Criterion.Builder<BetweenCriterion<T>> {

        @Override
        Builder<T> property(QProperty property);

        @Override
        Builder<T> operation(String operation);

        Builder<T> between(T min, T max);
    }

}
