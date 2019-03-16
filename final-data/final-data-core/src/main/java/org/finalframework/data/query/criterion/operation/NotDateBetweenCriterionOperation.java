package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.DefaultCriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:38:23
 * @since 1.0
 */
public abstract class NotDateBetweenCriterionOperation<T> extends AbsBetweenCriterionOperation<T> {

    @Override
    public final CriterionOperator operator() {
        return DefaultCriterionOperator.NOT_DATE_BETWEEN;
    }


}
