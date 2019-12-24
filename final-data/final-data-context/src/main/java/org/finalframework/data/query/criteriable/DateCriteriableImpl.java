package org.finalframework.data.query.criteriable;


import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.FunctionCriterion;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-24 15:36:09
 * @since 1.0
 */
public class DateCriteriableImpl<T> extends AbsCriteriable<T, String> implements DateCriteriable<Criterion> {

    public DateCriteriableImpl(QProperty<T> property) {
        super(property);
    }

    public DateCriteriableImpl(QProperty<T> property, FunctionCriterion function) {
        super(property, function);
    }

    public DateCriteriableImpl(QProperty<T> property, Collection<FunctionCriterion> functions) {
        super(property, functions);
    }


}

