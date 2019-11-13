package org.finalframework.data.query.criterion;

import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:52:10
 * @since 1.0
 */
public interface DoubleCriterion<T> extends Criterion<T> {

    static <T> Builder<T> builder() {
        return DoubleCriterionImpl.builder();
    }

    T min();

    T max();

    interface Builder<T> extends Criterion.Builder<DoubleCriterion<T>> {

        @Override
        Builder<T> property(QProperty property);

        @Override
        Builder<T> function(FunctionCriterion function);

        @Override
        Builder<T> function(Collection<FunctionCriterion> function);

        @Override
        Builder<T> operator(CriterionOperator operator);

        Builder<T> between(T min, T max);
    }

}
