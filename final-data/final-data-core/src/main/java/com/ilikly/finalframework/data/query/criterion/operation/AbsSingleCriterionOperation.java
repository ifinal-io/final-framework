package com.ilikly.finalframework.data.query.criterion.operation;

import com.ilikly.finalframework.data.query.criterion.SingleCriterion;
import com.ilikly.finalframework.data.query.criterion.SingleCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsSingleCriterionOperation<T> extends AbsCriterionOperation<T> implements SingleCriterionOperation<T> {

    @Override
    public final String format(SingleCriterion<T> criterion) {
        return format(criterion.property(), criterion.operator(), criterion.value());
    }

}
