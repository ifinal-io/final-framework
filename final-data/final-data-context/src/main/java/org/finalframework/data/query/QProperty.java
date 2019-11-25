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
    default Criterion dateEqual(@NonNull LocalDateTime date) {
        return date().eq(date);
    }

    @Override
    default Criterion dateEqual(@NonNull Date date) {
        return date().eq(date);
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
    default Criterion dateBetween(LocalDateTime min, LocalDateTime max) {
        return date().between(min, max);
    }

    @Override
    default Criterion dateBetween(@NonNull Date min, @NonNull Date max) {
        return date().between(min, max);
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
