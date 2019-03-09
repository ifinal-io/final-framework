package com.ilikly.finalframework.data.query.criterion.operation;


import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.DefaultCriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-26 10:31:28
 * @since 1.0
 */
public abstract class NotDateEqualCriterionOperation<T> extends AbsSingleCriterionOperation<T> {


    @Override
    public final CriterionOperator operator() {
        return DefaultCriterionOperator.DATE_EQUAL;
    }
}
