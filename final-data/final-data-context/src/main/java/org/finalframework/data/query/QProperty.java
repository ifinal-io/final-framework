package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.query.criteriable.*;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.function.SimpleFunctionCriterion;
import org.finalframework.data.query.criterion.function.SingleFunctionCriterion;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty<T> extends Criteriable<T, Criterion>, Sortable<Order>, ExecuteCriteriable<Object, Criterion> {

    static <T, E extends QEntity> QProperty.Builder<T> builder(E entity, Class<T> type) {
        return new QPropertyImpl.BuilderImpl<>(entity, type);
    }

    <E extends QEntity> E getEntity();

    Class<T> getType();

    String getPath();

    @NonNull
    String getTable();

    @NonNull
    String getName();

    @NonNull
    String getColumn();

    boolean isIdProperty();

    @NonNull
    PersistentType getPersistentType();

    Class<? extends TypeHandler> getTypeHandler();

    /**
     * Returns whether the property is an array.
     *
     * @return
     */
    boolean isArray();

    boolean unique();

    boolean nonnull();

    boolean isInsertable();

    boolean isUpdatable();

    boolean isSelectable();

    @Override
    default Criterion isNull() {
        return new AbsCriteriable<>(this).isNull();
    }

    @Override
    default Criterion isNotNull() {
        return new AbsCriteriable<>(this).isNotNull();
    }

    @Override
    default Criterion between(T min, T max) {
        return new AbsCriteriable<>(this).between(min, max);
    }

    @Override
    default Criterion notBetween(T min, T max) {
        return new AbsCriteriable<>(this).notBetween(min, max);
    }

    @Override
    default Criterion eq(T value) {
        return new AbsCriteriable<>(this).eq(value);
    }

    @Override
    default Criterion neq(T value) {
        return new AbsCriteriable<>(this).neq(value);
    }

    @Override
    default Criterion gt(T value) {
        return new AbsCriteriable<>(this).gt(value);
    }

    @Override
    default Criterion gte(T value) {
        return new AbsCriteriable<>(this).gte(value);
    }

    @Override
    default Criterion lt(T value) {
        return new AbsCriteriable<>(this).lt(value);
    }

    @Override
    default Criterion lte(T value) {
        return new AbsCriteriable<>(this).lte(value);
    }

    @Override
    default Criterion in(Collection<T> values) {
        return new AbsCriteriable<>(this).in(values);
    }

    @Override
    default Criterion nin(Collection<T> values) {
        return new AbsCriteriable<>(this).nin(values);
    }

    @Override
    default Criterion like(String prefix, String value, String suffix) {
        return new AbsCriteriable<>(this).like(prefix, value, suffix);
    }

    @Override
    default Criterion notLike(String prefix, String value, String suffix) {
        return new AbsCriteriable<>(this).notLike(prefix, value, suffix);
    }

    @Override
    default NumberCriteriable<Criterion> and(Object value) {
        return new NumberCriteriableImpl<>(this, new SingleFunctionCriterion<>(FunctionOperation.AND, value));
    }

    @Override
    default NumberCriteriable<Criterion> or(Object value) {
        return new NumberCriteriableImpl<>(this, new SingleFunctionCriterion<>(FunctionOperation.OR, value));
    }

    @Override
    default NumberCriteriable<Criterion> xor(Object value) {
        return new NumberCriteriableImpl<>(this, new SingleFunctionCriterion<>(FunctionOperation.XOR, value));
    }

    @Override
    default NumberCriteriable<Criterion> not() {
        return new NumberCriteriableImpl<>(this, new SimpleFunctionCriterion(FunctionOperation.AND));
    }

    @Override
    default DateCriteriable<Criterion> date() {
        return new DateCriteriableImpl<>(this, new SimpleFunctionCriterion(FunctionOperation.DATE));
    }

    @Override
    default NumberCriteriable<Criterion> min() {
        return new NumberCriteriableImpl<>(this, new SimpleFunctionCriterion(FunctionOperation.MIN));
    }

    @Override
    default NumberCriteriable<Criterion> max() {
        return new NumberCriteriableImpl<>(this, new SimpleFunctionCriterion(FunctionOperation.MAX));
    }

    @Override
    default NumberCriteriable<Criterion> sum() {
        return new NumberCriteriableImpl<>(this, new SimpleFunctionCriterion(FunctionOperation.SUM));
    }

    @Override
    default NumberCriteriable<Criterion> avg() {
        return new NumberCriteriableImpl<>(this, new SimpleFunctionCriterion(FunctionOperation.AVG));
    }

    @Override
    default FunctionCriteriable<Object, Criterion> extract(String path) {
        return new AbsCriteriable<>(this, new SingleFunctionCriterion<>(FunctionOperation.JSON_EXTRACT, path));
    }

    @Override
    default FunctionCriteriable<Object, Criterion> unquote() {
        return new AbsCriteriable<>(this, new SimpleFunctionCriterion(FunctionOperation.JSON_UNQUOTE));
    }

    @Override
    default Order asc() {
        return Order.asc(this);
    }

    @Override
    default Order desc() {
        return Order.desc(this);
    }


    interface Builder<T> extends org.finalframework.core.Builder<QProperty<T>> {

        Builder<T> path(String path);

        Builder<T> name(String name);

        Builder<T> column(String column);

        Builder<T> idProperty(boolean idProperty);

        Builder<T> persistentType(PersistentType persistentType);

        Builder<T> typeHandler(Class<? extends TypeHandler> typeHandler);

        Builder<T> insertable(boolean insertable);

        Builder<T> updatable(boolean updatable);

        Builder<T> selectable(boolean selectable);

    }
}
