package org.finalframework.data.query.criteriable;


import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.function.NumberFunctionCriterion;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 14:21:46
 * @since 1.0
 */
public class NumberCriteriableImpl<T> extends AbsCriteriable<T, Number> implements NumberCriteriable<T, Number, Criterion> {

    public NumberCriteriableImpl(QProperty<T> property) {
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
        addFunctionCriterion(NumberFunctionCriterion.and(value));
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> or(Number value) {
        addFunctionCriterion(NumberFunctionCriterion.or(value));
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> xor(Number value) {
        addFunctionCriterion(NumberFunctionCriterion.xor(value));
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> not() {
        addFunctionCriterion(NumberFunctionCriterion.not());
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> min() {
        addFunctionCriterion(NumberFunctionCriterion.min());
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> max() {
        addFunctionCriterion(NumberFunctionCriterion.max());
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> sum() {
        addFunctionCriterion(NumberFunctionCriterion.sum());
        return this;
    }

    @Override
    public NumberCriteriable<T, Number, Criterion> avg() {
        addFunctionCriterion(NumberFunctionCriterion.avg());
        return this;
    }
}

