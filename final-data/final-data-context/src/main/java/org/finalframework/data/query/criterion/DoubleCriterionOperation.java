package org.finalframework.data.query.criterion;

import org.finalframework.data.query.CriterionOperation;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.operation.DateBetweenCriterionOperation;
import org.finalframework.data.query.criterion.operation.NotBetweenCriterionOperation;
import org.finalframework.data.query.criterion.operation.NotDateBetweenCriterionOperation;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @see org.finalframework.data.query.criterion.operation.BetweenCriterionOperation
 * @see NotBetweenCriterionOperation
 * @see DateBetweenCriterionOperation
 * @see NotDateBetweenCriterionOperation
 * @since 1.0
 */
public interface DoubleCriterionOperation<T> extends CriterionOperation<T, DoubleCriterion<T>> {
    @Override
    default String format(DoubleCriterion<T> criterion) {
        return format(criterion.property(), criterion.functions(), criterion.operator(), criterion.min(), criterion.max());
    }

    String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max);
}
