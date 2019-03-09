package com.ilikly.finalframework.data.query.criterion.operation;

import com.ilikly.finalframework.data.query.BetweenCriterionOperation;
import com.ilikly.finalframework.data.query.criterion.BetweenCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsBetweenCriterionOperation<T> extends AbsCriterionOperation<T> implements BetweenCriterionOperation<T> {
    @Override
    public final String format(BetweenCriterion<T> criterion) {
        return format(criterion.property(), criterion.operator(), criterion.min(), criterion.max());
    }
}
