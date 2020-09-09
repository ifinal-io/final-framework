

package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.annotation.data.PersistentType;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.criteriable.ExecuteCriteriable;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.CriterionTarget;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.JsonOperations;
import org.finalframework.data.query.operation.LogicOperations;
import org.finalframework.data.query.operation.MathOperations;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty<T> extends Comparable<QProperty<T>>, Criteriable<Object, Criterion>, Sortable<Order>,
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

    Integer getOrder();

    @NonNull
    String getPath();

    @NonNull
    String getTable();

    @NonNull
    String getName();

    @Nullable
    default String getWriter() {
        return this.getProperty().getWriter();
    }

    @Nullable
    default String getReader() {
        return this.getProperty().getReader();
    }

    @NonNull
    String getColumn();

    boolean isIdProperty();

    boolean isReadable();

    /**
     * @return
     * @see Property#isWriteable()
     */
    boolean isWriteable();

    /**
     * @return
     * @see Property#isModifiable()
     */
    boolean isModifiable();

    @NonNull
    PersistentType getPersistentType();

    Class<? extends TypeHandler<?>> getTypeHandler();

    /**
     * Returns whether the property is an array.
     */
    boolean isArray();

    boolean hasView(@Nullable Class<?> view);

    boolean unique();

    boolean nonnull();

    default CriterionTarget<CriterionFunction> apply(Function<QProperty<T>, CriterionFunction> mapper) {
        return CriterionTarget.from(mapper.apply(this));
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
    default Criterion like(String value) {
        return CriterionTarget.from(this).like(value);
    }

    @Override
    default Criterion notLike(String value) {
        return CriterionTarget.from(this).notLike(value);
    }

    @Override
    default Criterion jsonContains(@NotNull Object value, String path) {
        return JsonOperations.contains(this, value, path);
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return JsonOperations.notContains(this, value, path);
    }


    @Override
    default CriterionTarget<CriterionFunction> date() {
        return apply(value -> DateOperation.date(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonExtract(String path) {
        return apply(value -> JsonOperations.extract(this, path));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonKeys() {
        return apply(value -> JsonOperations.keys(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonLength() {
        return apply(value -> JsonOperations.length(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonDepth() {
        return apply(value -> JsonOperations.depth(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> jsonUnquote() {
        return apply(value -> JsonOperations.unquote(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> and(Object value) {
        return apply(property -> LogicOperations.and(this, value));
    }

    @Override
    default CriterionTarget<CriterionFunction> or(Object value) {
        return apply(property -> LogicOperations.or(this, value));
    }

    @Override
    default CriterionTarget<CriterionFunction> xor(Object value) {
        return apply(property -> LogicOperations.xor(this, value));
    }

    @Override
    default CriterionTarget<CriterionFunction> not() {
        return apply(value -> LogicOperations.not(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> min() {
        return apply(value -> MathOperations.min(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> max() {
        return apply(value -> MathOperations.max(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> sum() {
        return apply(value -> MathOperations.sum(this));
    }

    @Override
    default CriterionTarget<CriterionFunction> avg() {
        return apply(value -> MathOperations.avg(this));
    }

    @Override
    default Order asc() {
        return Order.asc(this);
    }

    @Override
    default Order desc() {
        return Order.desc(this);
    }

    @Override
    default int compareTo(QProperty<T> o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

    interface Builder<T> extends org.finalframework.core.Builder<QProperty<T>> {

        Builder<T> order(Integer order);

        Builder<T> path(String path);

        Builder<T> name(String name);

        Builder<T> column(String column);

        Builder<T> idProperty(boolean idProperty);

        Builder<T> readable(boolean readable);

        Builder<T> writeable(boolean writeable);

        Builder<T> modifiable(boolean modifiable);

        Builder<T> persistentType(PersistentType persistentType);

        Builder<T> typeHandler(Class<? extends TypeHandler<?>> typeHandler);

        Builder<T> views(List<Class<?>> views);

        Builder<T> insertable(boolean insertable);

        Builder<T> updatable(boolean updatable);

        Builder<T> selectable(boolean selectable);

    }
}
