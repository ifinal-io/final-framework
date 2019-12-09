package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.criteriable.DateCriteriable;
import org.finalframework.data.query.criteriable.Executable;
import org.finalframework.data.query.criteriable.NumberCriteriable;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty<T> extends Criteriable<T, Criterion>, Sortable<Order>, Executable<T, T, Criterion> {

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
        return SingleCriterion.builder()
                .property(this)
                .operator(DefaultCriterionOperator.NULL)
                .build();
    }

    @Override
    default Criterion isNotNull() {
        return SingleCriterion.builder()
                .property(this)
                .operator(DefaultCriterionOperator.NOT_NULL)
                .build();
    }

    @Override
    default Criterion dateEqual(String date) {
        return date().eq(date);
    }

    @Override
    default Criterion dateEqual(@NonNull LocalDateTime date) {
        return date().eq(date);
    }

    @Override
    default Criterion dateEqual(@NonNull Date date) {
        return date().eq(date);
    }

    @Override
    default Criterion notDateEqual(String date) {
        return date().neq(date);
    }

    @Override
    default Criterion notDateEqual(@NonNull LocalDateTime date) {
        return date().neq(date);
    }

    @Override
    default Criterion notDateEqual(@NonNull Date date) {
        return date().neq(date);
    }

    @Override
    default Criterion dateEqual(@NonNull long date) {
        return date().eq(date);
    }

    @Override
    default Criterion notDateEqual(@NonNull long date) {
        return date().neq(date);
    }

    @Override
    default Criterion dateBefore(String date) {
        return date().before(date);
    }

    @Override
    default Criterion dateBefore(LocalDateTime date) {
        return date().before(date);
    }

    @Override
    default Criterion dateBefore(@NonNull Date date) {
        return date().before(date);
    }

    @Override
    default Criterion dateBefore(@NonNull long date) {
        return date().before(date);
    }

    @Override
    default Criterion dateAfter(String date) {
        return date().after(date);
    }

    @Override
    default Criterion dateAfter(LocalDateTime date) {
        return date().after(date);
    }

    @Override
    default Criterion dateAfter(@NonNull Date date) {
        return date().after(date);
    }

    @Override
    default Criterion dateAfter(@NonNull long date) {
        return date().after(date);
    }

    @Override
    default Criterion dateBetween(String min, String max) {
        return date().between(min, max);
    }

    @Override
    default Criterion dateBetween(LocalDateTime min, LocalDateTime max) {
        return date().between(min, max);
    }

    @Override
    default Criterion dateBetween(@NonNull Date min, @NonNull Date max) {
        return date().between(min, max);
    }

    @Override
    default Criterion notDateBetween(String min, String max) {
        return date().notBetween(min, max);
    }

    @Override
    default Criterion notDateBetween(LocalDateTime min, LocalDateTime max) {
        return date().notBetween(min, max);
    }

    @Override
    default Criterion notDateBetween(@NonNull Date min, @NonNull Date max) {
        return date().notBetween(min, max);
    }

    @Override
    default Criterion dateBetween(@NonNull long min, @NonNull long max) {
        return date().between(min, max);
    }

    @Override
    default Criterion notDateBetween(@NonNull long min, @NonNull long max) {
        return date().notBetween(min, max);
    }

    @Override
    default Criterion maxEqual(Number value) {
        return max().eq(value);
    }

    @Override
    default Criterion maxNotEqual(Number value) {
        return max().neq(value);
    }

    @Override
    default Criterion maxGreaterThan(Number value) {
        return max().gt(value);
    }

    @Override
    default Criterion maxGreaterThanEqual(Number value) {
        return max().gte(value);
    }

    @Override
    default Criterion maxLessThan(Number value) {
        return max().lt(value);
    }

    @Override
    default Criterion maxLessThanEqual(Number value) {
        return max().lte(value);
    }

    @Override
    default Criterion maxIn(Collection<Number> values) {
        return max().in(values);
    }

    @Override
    default Criterion maxNotIn(Collection<Number> values) {
        return max().nin(values);
    }

    @Override
    default Criterion maxBetween(Number min, Number max) {
        return max().between(min, max);
    }

    @Override
    default Criterion maxNotBetween(Number min, Number max) {
        return max().notBetween(min, max);
    }

    @Override
    default Criterion minEqual(Number value) {
        return min().eq(value);
    }

    @Override
    default Criterion minNotEqual(Number value) {
        return min().neq(value);
    }

    @Override
    default Criterion minGreaterThan(Number value) {
        return min().gt(value);
    }

    @Override
    default Criterion minGreaterThanEqual(Number value) {
        return min().gte(value);
    }

    @Override
    default Criterion minLessThan(Number value) {
        return min().lt(value);
    }

    @Override
    default Criterion minLessThanEqual(Number value) {
        return min().lte(value);
    }

    @Override
    default Criterion minIn(Collection<Number> values) {
        return min().in(values);
    }

    @Override
    default Criterion minNotIn(Collection<Number> values) {
        return min().nin(values);
    }

    @Override
    default Criterion minBetween(Number min, Number max) {
        return min().between(min, max);
    }

    @Override
    default Criterion minNotBetween(Number min, Number max) {
        return max().notBetween(min, max);
    }

    @Override
    default Criterion sumEqual(Number value) {
        return sum().eq(value);
    }

    @Override
    default Criterion sumNotEqual(Number value) {
        return sum().neq(value);
    }

    @Override
    default Criterion sumGreaterThan(Number value) {
        return sum().gt(value);
    }

    @Override
    default Criterion sumGreaterThanEqual(Number value) {
        return sum().gte(value);
    }

    @Override
    default Criterion sumLessThan(Number value) {
        return sum().lt(value);
    }

    @Override
    default Criterion sumLessThanEqual(Number value) {
        return sum().lte(value);
    }

    @Override
    default Criterion sumIn(Collection<Number> values) {
        return sum().in(values);
    }

    @Override
    default Criterion sumNotIn(Collection<Number> values) {
        return sum().nin(values);
    }

    @Override
    default Criterion sumBetween(Number min, Number max) {
        return sum().between(min, max);
    }

    @Override
    default Criterion sumNotBetween(Number min, Number max) {
        return sum().notBetween(min, max);
    }

    @Override
    default Criterion avgEqual(Number value) {
        return avg().eq(value);
    }

    @Override
    default Criterion avgNotEqual(Number value) {
        return avg().neq(value);
    }

    @Override
    default Criterion avgGreaterThan(Number value) {
        return avg().gt(value);
    }

    @Override
    default Criterion avgGreaterThanEqual(Number value) {
        return avg().gte(value);
    }

    @Override
    default Criterion avgLessThan(Number value) {
        return avg().lt(value);
    }

    @Override
    default Criterion avgLessThanEqual(Number value) {
        return avg().lte(value);
    }

    @Override
    default Criterion avgIn(Collection<Number> values) {
        return avg().in(values);
    }

    @Override
    default Criterion avgNotIn(Collection<Number> values) {
        return avg().nin(values);
    }

    @Override
    default Criterion avgBetween(Number min, Number max) {
        return avg().between(min, max);
    }

    @Override
    default Criterion avgNotBetween(Number min, Number max) {
        return avg().notBetween(min, max);
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
    default DateCriteriable<T, Criterion> date() {
        return DateCriteriable.date(this);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> and(T value) {
        return NumberCriteriable.and(this, value);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> or(T value) {
        return NumberCriteriable.or(this, value);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> xor(T value) {
        return NumberCriteriable.xor(this, value);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> not() {
        return NumberCriteriable.not(this);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> min() {
        return NumberCriteriable.min(this);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> max() {
        return NumberCriteriable.max(this);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> sum() {
        return NumberCriteriable.sum(this);
    }

    @Override
    default NumberCriteriable<T, Number, Criterion> avg() {
        return NumberCriteriable.avg(this);
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
