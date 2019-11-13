package org.finalframework.data.query.criterion;

import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.operation.InCriterionOperation;
import org.finalframework.data.query.criterion.operation.NotInCriterionOperation;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @see CollectionCriterionOperation
 * @see InCriterionOperation
 * @see NotInCriterionOperation
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

        @Override
        Builder<T> function(FunctionCriterion function);

        @Override
        Builder<T> function(Collection<FunctionCriterion> functions);

        @Override
        Builder<T> operator(CriterionOperator operator);

        @Override
        Builder<T> value(Collection<T> value);
    }

}
