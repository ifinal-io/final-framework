package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.annotation.enums.PersistentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty<T> extends Criteriable<Criteria>, Sortable<Sort> {

    Class<T> getType();

    Class getComponentType();

    String getPath();

    @NotNull
    String getTable();

    @NotNull
    String getName();

    @NotNull
    String getColumn();

    @NotNull
    PersistentType getPersistentType();

    boolean isEntity();

    boolean isIdProperty();

    boolean isEnum();

    boolean isCollectionLike();

    boolean isMap();

    boolean isTransient();

    /**
     * Returns whether the property is an array.
     *
     * @return
     */
    boolean isArray();

    boolean unique();

    boolean nonnull();

    boolean insertable();

    boolean updatable();

    <A extends Annotation> A findAnnotation(Class<A> ann);

    default <A extends Annotation> boolean hasAnnotation(Class<A> ann) {
        return findAnnotation(ann) != null;
    }

    @Override
    default Criteria eq(@NotNull Object value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.EQUAL.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria neq(@NotNull Object value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NOT_EQUAL.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria gt(@NotNull Comparable value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.GREATER_THAN.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria gte(@NotNull Comparable value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.GREATER_EQUAL_THAN.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria lt(@NotNull Comparable value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.LESS_THAN.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria lte(@NotNull Comparable value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.LESS_EQUAL_THAN.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria in(@NotEmpty Object... values) {
        return in(Arrays.asList(values));
    }

    @Override
    default Criteria in(@NotEmpty Collection<Object> values) {
        return Criteria.where(
                CollectionCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.IN.name())
                        .value(values)
                        .build()
        );
    }

    @Override
    default Criteria nin(@NotEmpty Object... values) {
        return nin(Arrays.asList(values));
    }

    @Override
    default Criteria nin(@NotEmpty Collection<Object> values) {
        return Criteria.where(
                CollectionCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NOT_IN.name())
                        .value(values)
                        .build()
        );
    }

    @Override
    default Criteria isNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NULL.name())
                        .build()
        );
    }

    @Override
    default Criteria nonNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NOT_NULL.name())
                        .build()
        );
    }

    @Override
    default Criteria startWith(@NotEmpty String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.START_WITH.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notStartWith(@NotEmpty String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NOT_START_WITH.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria endWith(@NotEmpty String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.END_WITH.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notEndWith(@NotEmpty String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NOT_END_WITH.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria contains(@NotEmpty String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.CONTAINS.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notContains(@NotEmpty String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NOT_CONTAINS.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria like(@NotBlank String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.LIKE.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notLike(@NotBlank String value) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.NOT_LIKE.name())
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria before(@NotNull Date date) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.BEFORE.name())
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria before(@NotNull long date) {
        return before(new Date(date));
    }

    @Override
    default Criteria after(@NotNull Date date) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.AFTER.name())
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria after(@NotNull long date) {
        return after(new Date(date));
    }

    @Override
    default Criteria dateBefore(@NotNull Date date) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.DATE_BEFORE.name())
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria dateBefore(@NotNull long date) {
        return dateBefore(new Date(date));
    }

    @Override
    default Criteria dateAfter(@NotNull Date date) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operation(CriterionOperations.DATE_AFTER.name())
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria dateAfter(@NotNull long date) {
        return dateAfter(new Date(date));
    }

    @Override
    default <E extends Comparable<E>> Criteria between(@NotNull E min, @NotNull E max) {
        final BetweenCriterion.Builder<E> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operation(CriterionOperations.BETWEEN.name())
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default <E extends Comparable<E>> Criteria notBetween(@NotNull E min, @NotNull E max) {
        final BetweenCriterion.Builder<E> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operation(CriterionOperations.NOT_BETWEEN.name())
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default Criteria dateBetween(@NotNull Date min, @NotNull Date max) {
        final BetweenCriterion.Builder<Date> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operation(CriterionOperations.DATE_BETWEEN.name())
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default Criteria notDateBetween(@NotNull Date min, @NotNull Date max) {
        final BetweenCriterion.Builder<Date> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operation(CriterionOperations.NOT_DATE_BETWEEN.name())
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default Criteria dateBetween(@NotNull long min, @NotNull long max) {
        return dateBetween(new Date(min), new Date(max));
    }

    @Override
    default Criteria notDateBetween(@NotNull long min, @NotNull long max) {
        return notDateBetween(new Date(min), new Date(max));
    }

    @Override
    default Sort asc() {
        return Sort.asc(this);
    }

    @Override
    default Sort desc() {
        return Sort.desc(this);
    }

}
