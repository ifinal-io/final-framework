package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.condition.BetweenCondition;
import org.finalframework.data.query.condition.BitFunctionCondition;
import org.finalframework.data.query.condition.CompareCondition;
import org.finalframework.data.query.condition.InCondition;
import org.finalframework.data.query.function.DefaultFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 13:47:09
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public interface NumberCriteriable<T, V, R> extends BitFunctionCondition<V, NumberCriteriable<T, Number, R>>,
        CompareCondition<Number, R>, BetweenCondition<Number, R>, InCondition<Number, R> {

    static <T, V, R> NumberCriteriable<T, Number, R> and(QProperty<T> property, V value) {
        if (value instanceof Number) {
            return new NumberCriteriableImpl(property, DefaultFunctionCriterion.and((Number) value));
        }
        throw new IllegalArgumentException();
    }

    static <T, V, R> NumberCriteriable<T, Number, R> or(QProperty<T> property, V value) {
        if (value instanceof Number) {
            return new NumberCriteriableImpl(property, DefaultFunctionCriterion.or((Number) value));
        }
        throw new IllegalArgumentException();
    }

    static <T, V, R> NumberCriteriable<T, Number, R> xor(QProperty<T> property, V value) {
        if (value instanceof Number) {
            return new NumberCriteriableImpl(property, DefaultFunctionCriterion.xor((Number) value));
        }
        throw new IllegalArgumentException();
    }

    static <T, R> NumberCriteriable<T, Number, R> not(QProperty<T> property) {
        return new NumberCriteriableImpl(property, DefaultFunctionCriterion.not());
    }

}
