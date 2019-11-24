package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @see SingleCriterionOperation
 * @since 1.0
 */
public interface SingleCriterion<T> extends Criterion<T> {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T getValue();

    interface Builder<T> extends Criterion.Builder<SingleCriterion<T>> {

        @Override
        Builder<T> property(QProperty property);

        @Override
        Builder<T> function(FunctionCriterion function);

        @Override
        Builder<T> function(Collection<FunctionCriterion> functions);

        @Override
        Builder<T> operator(CriterionOperator operation);

        @Override
        Builder<T> typeHandler(Class<? extends TypeHandler> typeHandler);

        Builder<T> value(T value);
    }

}
