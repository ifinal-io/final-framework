package org.finalframework.data.query;

import org.finalframework.data.query.criterion.BetweenCriterion;
import org.finalframework.data.query.criterion.operation.DateBetweenCriterionOperation;
import org.finalframework.data.query.criterion.operation.NotBetweenCriterionOperation;
import org.finalframework.data.query.criterion.operation.NotDateBetweenCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 * @see org.finalframework.data.query.criterion.operation.BetweenCriterionOperation
 * @see NotBetweenCriterionOperation
 * @see DateBetweenCriterionOperation
 * @see NotDateBetweenCriterionOperation
 */
public interface BetweenCriterionOperation<T> extends CriterionOperation<T, BetweenCriterion<T>> {
    @Override
    default String format(BetweenCriterion<T> criterion) {
        return format(criterion.property(), criterion.operator(), criterion.min(), criterion.max());
    }

    String format(QProperty property, CriterionOperator operator, T min, T max);
}
