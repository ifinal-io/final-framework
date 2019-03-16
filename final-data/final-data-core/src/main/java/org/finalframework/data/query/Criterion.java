package org.finalframework.data.query;

import org.finalframework.data.query.criterion.BetweenCriterion;
import org.finalframework.data.query.criterion.CollectionCriterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 12:19:55
 * @see SingleCriterion
 * @see CollectionCriterion
 * @see BetweenCriterion
 * @since 1.0
 */
public interface Criterion<T> {

    @NonNull
    QProperty property();

    @NonNull
    CriterionOperator operator();


    interface Builder<T> extends org.finalframework.core.Builder<Criterion> {
        @NonNull
        Builder<T> property(@NonNull QProperty property);

        @NonNull
        Builder<T> operator(@NonNull CriterionOperator operator);

    }

}
