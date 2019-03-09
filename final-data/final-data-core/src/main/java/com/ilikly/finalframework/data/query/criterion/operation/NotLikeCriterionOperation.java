package com.ilikly.finalframework.data.query.criterion.operation;

import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.DefaultCriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:38:04
 * @since 1.0
 */
public abstract class NotLikeCriterionOperation<T> extends AbsSingleCriterionOperation<T> {
    @Override
    public final CriterionOperator operator() {
        return DefaultCriterionOperator.NOT_LIKE;
    }


}
