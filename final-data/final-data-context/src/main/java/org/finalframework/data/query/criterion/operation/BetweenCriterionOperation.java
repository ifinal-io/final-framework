package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.criterion.operator.DefaultCriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:38:23
 * @since 1.0
 */
public abstract class BetweenCriterionOperation<T> extends AbsBetweenCriterionOperation<T> {
    @Override
    public final CriterionOperator operator() {
        return DefaultCriterionOperator.BETWEEN;
    }

}
