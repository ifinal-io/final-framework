package org.finalframework.data.query;

import org.finalframework.data.query.criterion.CollectionCriterion;
import org.finalframework.data.query.criterion.DoubleCriterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 12:19:55
 * @see SingleCriterion
 * @see DoubleCriterion
 * @see CollectionCriterion
 * @since 1.0
 */
public interface Criterion<T> {

    @NonNull
    QProperty property();

    @Nullable
    Collection<FunctionCriterion> functions();

    @NonNull
    CriterionOperator operator();


    interface Builder<T> extends org.finalframework.core.Builder<Criterion> {
        @NonNull
        Builder<T> property(@NonNull QProperty property);

        @NonNull
        Builder<T> function(@NonNull FunctionCriterion function);

        @NonNull
        Builder<T> function(Collection<FunctionCriterion> functions);

        @NonNull
        Builder<T> operator(@NonNull CriterionOperator operator);

    }

}
