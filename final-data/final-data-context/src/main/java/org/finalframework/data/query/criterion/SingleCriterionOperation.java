package org.finalframework.data.query.criterion;

import org.finalframework.data.query.CriterionOperation;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.operation.*;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @see EqualCriterionOperation
 * @see NotEqualCriterionOperation
 * @see GreaterThanCriterionOperation
 * @see GreaterThanEqualCriterionOperation
 * @see LessThanCriterionOperation
 * @see LessThanEqualCriterionOperation
 * @see StartWithCriterionOperation
 * @see NotStartWithCriterionOperation
 * @see EndWithCriterionOperation
 * @see NotEndWithCriterionOperation
 * @see ContainsCriterionOperation
 * @see NotContainsCriterionOperation
 * @see LikeCriterionOperation
 * @see BeforeCriterionOperation
 * @see AfterCriterionOperation
 * @see DateBeforeCriterionOperation
 * @see DateAfterCriterionOperation
 * @since 1.0
 */
public interface SingleCriterionOperation<T> extends CriterionOperation<T, SingleCriterion<T>> {

    @Override
    default String format(SingleCriterion<T> criterion) {
        return format(criterion.getProperty(), criterion.getFunctions(), criterion.getOperator(), criterion.getValue());
    }

    String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value);
}
