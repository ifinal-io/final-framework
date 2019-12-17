package org.finalframework.data.query.criterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:52:10
 * @since 1.0
 */
public interface BetweenCriterion<T> extends Criterion {

    static <T> Builder<T> builder() {
        return BetweenCriterionImpl.builder();
    }

    T min();

    T max();

    interface Builder<T> extends Criterion.Builder<BetweenCriterion<T>, Builder<T>> {

        Builder<T> between(T min, T max);
    }

}
