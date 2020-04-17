package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.criteriable.AbsCriteriable;
import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.criteriable.ExecuteCriteriable;
import org.finalframework.data.query.criteriable.FunctionCriteriable;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;
import org.finalframework.data.query.operation.JsonOperation;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty<T> extends Criteriable<T, Criterion>, Sortable<Order>,
        ExecuteCriteriable<Object, Criterion> {

    static <T, E extends QEntity<?, ?>> QProperty.Builder<T> builder(E entity, Property property) {
        return new QPropertyImpl.BuilderImpl<>(entity, property);
    }

    <E extends QEntity<?, ?>> E getEntity();

    @NonNull
    Property getProperty();

    @NonNull
    default Class<T> getType() {
        return (Class<T>) getProperty().getJavaType();
    }

    @NonNull
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
     */
    boolean isArray();

    boolean unique();

    boolean nonnull();

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
        return new AbsCriteriable<>(this).in((Collection) values);
    }

    @Override
    default Criterion nin(Collection<T> values) {
        return new AbsCriteriable<>(this).nin((Collection) values);
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
    default FunctionCriteriable<Object, Criterion> jsonExtract(String path) {
        return new AbsCriteriable<>(this, new SingleFunctionOperation<>(JsonOperation.JSON_EXTRACT, path));
    }

    @Override
    default FunctionCriteriable<Object, Criterion> jsonKeys() {
        return new AbsCriteriable<>(this, new SimpleFunctionOperation(JsonOperation.JSON_KEYS));

    }

    @Override
    default FunctionCriteriable<Object, Criterion> jsonLength() {
        return new AbsCriteriable<>(this, new SimpleFunctionOperation(JsonOperation.JSON_LENGTH));

    }

    @Override
    default FunctionCriteriable<Object, Criterion> jsonDepth() {
        return new AbsCriteriable<>(this, new SimpleFunctionOperation(JsonOperation.JSON_DEPTH));
    }

    @Override
    default FunctionCriteriable<Object, Criterion> jsonUnquote() {
        return new AbsCriteriable<>(this, new SimpleFunctionOperation(JsonOperation.JSON_UNQUOTE));
    }

    @Override
    default Criterion jsonContains(@NotNull Object value, String path) {
        return new AbsCriteriable<>(this, new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path))
                .eq(true);
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return new AbsCriteriable<>(this, new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path))
                .neq(true);
    }


    @Override
    default FunctionCriteriable<Object, Criterion> and(Object value) {
        return new AbsCriteriable<>(this).and(value);
    }

    @Override
    default FunctionCriteriable<Object, Criterion> or(Object value) {
        return new AbsCriteriable<>(this).or(value);
    }

    @Override
    default FunctionCriteriable<Object, Criterion> xor(Object value) {
        return new AbsCriteriable<>(this).xor(value);
    }

    @Override
    default FunctionCriteriable<Object, Criterion> not() {
        return new AbsCriteriable<>(this).not();
    }

    @Override
    default FunctionCriteriable<Object, Criterion> date() {
        return new AbsCriteriable<>(this).date();
    }

    @Override
    default FunctionCriteriable<Object, Criterion> min() {
        return new AbsCriteriable<>(this).min();
    }

    @Override
    default FunctionCriteriable<Object, Criterion> max() {
        return new AbsCriteriable<>(this).max();
    }

    @Override
    default FunctionCriteriable<Object, Criterion> sum() {
        return new AbsCriteriable<>(this).sum();
    }

    @Override
    default FunctionCriteriable<Object, Criterion> avg() {
        return new AbsCriteriable<>(this).avg();
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
