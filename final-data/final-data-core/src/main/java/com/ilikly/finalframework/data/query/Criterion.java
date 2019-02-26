package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.query.criterion.BetweenCriterion;
import com.ilikly.finalframework.data.query.criterion.CollectionCriterion;
import com.ilikly.finalframework.data.query.criterion.CriterionOperator;
import com.ilikly.finalframework.data.query.criterion.SingleCriterion;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 12:19:55
 * @since 1.0
 * @see SingleCriterion
 * @see CollectionCriterion
 * @see BetweenCriterion
 */
public interface Criterion<T> {

    @NonNull
    QProperty property();

    @NonNull
    CriterionOperator operator();


    interface Builder<T> extends com.ilikly.finalframework.core.Builder<Criterion> {
        @NonNull
        Builder<T> property(@NonNull QProperty property);

        @NonNull
        Builder<T> operator(@NonNull CriterionOperator operator);

    }

}
