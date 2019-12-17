package org.finalframework.data.query.criteriable;


import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.function.SimpleFunctionCriterion;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-23 23:44:10
 * @since 1.0
 */
public class DateCriteriableImpl<T> extends AbsCriteriable<T, String> implements DateCriteriable<T, Criterion> {

    public DateCriteriableImpl(QProperty<T> property) {
        super(property, new SimpleFunctionCriterion(FunctionOperation.DATE));
    }

}

