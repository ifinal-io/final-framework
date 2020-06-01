package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.criteriable.ExecuteCriteriable;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.CriterionTarget;
import org.finalframework.data.query.operation.function.Function;
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.LogicOperation;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.finalframework.data.query.operation.Operation.MathOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty<T> extends Criteriable<Object, Criterion>, Sortable<Order>,
        ExecuteCriteriable<T, Criterion> {

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

    default CriterionTarget<QProperty<T>, Object> apply(Function function) {
        return CriterionTarget.from(this).apply(function);
    }

    @Override
    default Criterion isNull() {
        return CriterionTarget.from(this).isNull();
    }

    @Override
    default Criterion isNotNull() {
        return CriterionTarget.from(this).isNotNull();
    }

    @Override
    default Criterion between(Object min, Object max) {
        return CriterionTarget.from(this).between(min, max);
    }

    @Override
    default Criterion notBetween(Object min, Object max) {
        return CriterionTarget.from(this).notBetween(min, max);
    }

    @Override
    default Criterion eq(Object value) {
        return CriterionTarget.from(this).eq(value);
    }

    @Override
    default Criterion neq(Object value) {
        return CriterionTarget.from(this).neq(value);
    }

    @Override
    default Criterion gt(Object value) {
        return CriterionTarget.from(this).gt(value);
    }

    @Override
    default Criterion gte(Object value) {
        return CriterionTarget.from(this).gte(value);
    }

    @Override
    default Criterion lt(Object value) {
        return CriterionTarget.from(this).lt(value);
    }

    @Override
    default Criterion lte(Object value) {
        return CriterionTarget.from(this).lte(value);
    }

    @Override
    default Criterion in(Collection<Object> values) {
        return CriterionTarget.from(this).in((Collection) values);
    }

    @Override
    default Criterion nin(Collection<Object> values) {
        return CriterionTarget.from(this).nin((Collection) values);
    }

    @Override
    default Criterion like(String prefix, String value, String suffix) {
        return CriterionTarget.from(this).like(prefix, value, suffix);
    }

    @Override
    default Criterion notLike(String prefix, String value, String suffix) {
        return CriterionTarget.from(this).notLike(prefix, value, suffix);
    }

    @Override
    default Criterion jsonContains(@NotNull Object value, String path) {
        return CriterionTarget.from(this).jsonContains(value, path);
//        return new AbsCriteriable<>(this, new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path))
//                .eq(true);
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return CriterionTarget.from(this).notJsonContains(value, path);
//        return new AbsCriteriable<>(this, new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path))
//                .neq(true);
    }


    @Override
    default CriterionTarget<QProperty<T>, Object> date() {
        return apply(DateOperation.date());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> jsonExtract(String path) {
        return apply(JsonOperation.extract(path));
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> jsonKeys() {
        return apply(JsonOperation.keys());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> jsonLength() {
        return apply(JsonOperation.length());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> jsonDepth() {
        return apply(JsonOperation.depth());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> jsonUnquote() {
        return apply(JsonOperation.unquote());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> and(Object value) {
        return apply(LogicOperation.and(value));
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> or(Object value) {
        return apply(LogicOperation.or(value));
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> xor(Object value) {
        return apply(LogicOperation.xor(value));
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> not() {
        return apply(LogicOperation.not());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> min() {
        return apply(MathOperation.min());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> max() {
        return apply(MathOperation.max());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> sum() {
        return apply(MathOperation.sum());
    }

    @Override
    default CriterionTarget<QProperty<T>, Object> avg() {
        return apply(MathOperation.avg());
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
