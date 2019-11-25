package org.finalframework.data.query.criteriable;


import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.function.DefaultFunctionCriterion;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 14:21:46
 * @since 1.0
 */
public class NumberCriteriableImpl<T> extends AbsCriteriable<T, Number> implements NumberCriteriable<T, Number, Criterion> {

    NumberCriteriableImpl(QProperty<T> property) {
        super(property);
    }

    NumberCriteriableImpl(QProperty<T> property, FunctionCriterion functions) {
        super(property, functions);
    }

    NumberCriteriableImpl(QProperty<T> property, Collection<FunctionCriterion> functions) {
        super(property, functions);
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> and(Number value) {
        addFunctionCriterion(DefaultFunctionCriterion.and(value));
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> or(Number value) {
        addFunctionCriterion(DefaultFunctionCriterion.or(value));
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> xor(Number value) {
        addFunctionCriterion(DefaultFunctionCriterion.xor(value));
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> not() {
        addFunctionCriterion(DefaultFunctionCriterion.not());
        return this;
    }

}

