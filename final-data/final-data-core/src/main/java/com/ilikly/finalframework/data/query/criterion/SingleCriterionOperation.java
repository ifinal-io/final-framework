package com.ilikly.finalframework.data.query.criterion;

import com.ilikly.finalframework.data.query.CriterionOperation;
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.criterion.operation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 * @see EqualCriterionOperation
 * @see NotEqualCriterionOperation
 * @see GreaterThanCriterionOperation
 * @see GreaterEqualThanCriterionOperation
 * @see LessThanCriterionOperation
 * @see LessEqualThanCriterionOperation
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
 */
public interface SingleCriterionOperation<T> extends CriterionOperation<T, SingleCriterion<T>> {

    @Override
    default String format(SingleCriterion<T> criterion) {
        return format(criterion.property(), criterion.operator(), criterion.value());
    }

    String format(QProperty property, CriterionOperator operator, T value);
}
