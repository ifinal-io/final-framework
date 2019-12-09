package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.condition.*;
import org.finalframework.data.query.function.NumberFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 13:47:09
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public interface NumberCriteriable<T, V, R> extends BitFunctionCondition<V, NumberCriteriable<T, Number, R>>,
        MathFunctionCondition<V, NumberCriteriable<T, Number, R>>,
        CompareCondition<Number, R>, BetweenCondition<Number, R>, InCondition<Number, R> {

    static <T, V, R> NumberCriteriable<T, Number, R> and(QProperty<T> property, V value) {
        if (value instanceof Number) {
            return new NumberCriteriableImpl(property, NumberFunctionCriterion.and((Number) value));
        }
        throw new IllegalArgumentException();
    }

    static <T, V, R> NumberCriteriable<T, Number, R> or(QProperty<T> property, V value) {
        if (value instanceof Number) {
            return new NumberCriteriableImpl(property, NumberFunctionCriterion.or((Number) value));
        }
        throw new IllegalArgumentException();
    }

    static <T, V, R> NumberCriteriable<T, Number, R> xor(QProperty<T> property, V value) {
        if (value instanceof Number) {
            return new NumberCriteriableImpl(property, NumberFunctionCriterion.xor((Number) value));
        }
        throw new IllegalArgumentException();
    }

    static <T, R> NumberCriteriable<T, Number, R> not(QProperty<T> property) {
        return new NumberCriteriableImpl(property, NumberFunctionCriterion.not());
    }


    static <T, R> NumberCriteriable<T, Number, R> min(QProperty<T> property) {
        return new NumberCriteriableImpl(property, NumberFunctionCriterion.min());
    }


    static <T, R> NumberCriteriable<T, Number, R> max(QProperty<T> property) {
        return new NumberCriteriableImpl(property, NumberFunctionCriterion.max());
    }


    static <T, R> NumberCriteriable<T, Number, R> sum(QProperty<T> property) {
        return new NumberCriteriableImpl(property, NumberFunctionCriterion.sum());
    }


    static <T, R> NumberCriteriable<T, Number, R> avg(QProperty<T> property) {
        return new NumberCriteriableImpl(property, NumberFunctionCriterion.avg());
    }

}
