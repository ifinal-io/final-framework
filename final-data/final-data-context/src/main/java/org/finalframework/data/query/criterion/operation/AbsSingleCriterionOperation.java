package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.criterion.SingleCriterion;
import org.finalframework.data.query.criterion.SingleCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsSingleCriterionOperation<T> extends AbsCriterionOperation<T> implements SingleCriterionOperation<T> {

    @Override
    public final String format(SingleCriterion<T> criterion) {
        return format(criterion.getProperty(), criterion.getFunctions(), criterion.getOperator(), criterion.getValue());
    }

}
