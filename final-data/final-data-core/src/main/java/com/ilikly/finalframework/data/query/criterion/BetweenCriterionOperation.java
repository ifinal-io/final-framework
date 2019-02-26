package com.ilikly.finalframework.data.query.criterion;

import com.ilikly.finalframework.data.query.CriterionOperation;
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.criterion.operation.DateBetweenCriterionOperation;
import com.ilikly.finalframework.data.query.criterion.operation.NotBetweenCriterionOperation;
import com.ilikly.finalframework.data.query.criterion.operation.NotDateBetweenCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 * @see com.ilikly.finalframework.data.query.criterion.operation.BetweenCriterionOperation
 * @see NotBetweenCriterionOperation
 * @see DateBetweenCriterionOperation
 * @see NotDateBetweenCriterionOperation
 */
public interface BetweenCriterionOperation<T extends Comparable<T>> extends CriterionOperation<T, BetweenCriterion<T>> {
    @Override
    default String format(BetweenCriterion<T> criterion) {
        return format(criterion.property(), criterion.operator(), criterion.min(), criterion.max());
    }

    String format(QProperty property, CriterionOperator operator, T min, T max);
}
