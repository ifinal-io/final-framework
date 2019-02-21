package com.ilikly.finalframework.data.query;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @since 1.0
 */
public interface CollectionCriterion<T> extends SingleCriterion<Collection<T>> {

    static <T> Builder<T> builder() {
        return CollectionCriterionImpl.builder();
    }

    Collection<T> value();

    interface Builder<T> extends SingleCriterion.Builder<Collection<T>> {

        @Override
        Builder<T> property(QProperty property);

        Builder<T> operator(CriterionOperator operator);

        @Override
        Builder<T> value(Collection<T> value);
    }

}
