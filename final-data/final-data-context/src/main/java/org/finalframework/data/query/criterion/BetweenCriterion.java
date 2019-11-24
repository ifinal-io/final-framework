package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:52:10
 * @since 1.0
 */
public interface BetweenCriterion<T> extends Criterion<T> {

    static <T> Builder<T> builder() {
        return BetweenCriterionImpl.builder();
    }

    T min();

    T max();

    interface Builder<T> extends Criterion.Builder<BetweenCriterion<T>> {

        @Override
        Builder<T> property(QProperty property);

        @Override
        Builder<T> function(FunctionCriterion function);

        @Override
        Builder<T> function(Collection<FunctionCriterion> function);

        @Override
        Builder<T> operator(CriterionOperator operator);

        @Override
        Builder<T> typeHandler(@NonNull Class<? extends TypeHandler> typeHandler);

        Builder<T> between(T min, T max);
    }

}
