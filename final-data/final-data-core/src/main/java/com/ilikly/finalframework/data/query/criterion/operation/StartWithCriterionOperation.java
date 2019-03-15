package com.ilikly.finalframework.data.query.criterion.operation;

import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.DefaultCriterionOperator;
import com.ilikly.finalframework.data.query.criterion.SingleCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:38:04
 * @since 1.0
 */
public abstract class StartWithCriterionOperation<T> extends AbsCriterionOperation<T> implements SingleCriterionOperation<T> {

    @Override
    public final CriterionOperator operator() {
        return DefaultCriterionOperator.START_WITH;
    }


}
