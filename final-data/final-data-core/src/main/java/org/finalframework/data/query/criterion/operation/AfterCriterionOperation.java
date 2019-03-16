package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.DefaultCriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:52:04
 * @since 1.0
 */
public abstract class AfterCriterionOperation<T> extends AbsSingleCriterionOperation<T> {

    @Override
    public final CriterionOperator operator() {
        return DefaultCriterionOperator.AFTER;
    }


}
