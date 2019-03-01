package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import com.ilikly.finalframework.data.query.criterion.BetweenCriterion;
import com.ilikly.finalframework.data.query.criterion.CollectionCriterion;
import com.ilikly.finalframework.data.query.criterion.CriterionOperators;
import com.ilikly.finalframework.data.query.criterion.SingleCriterion;

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
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria neq(@NotNull Object value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria gt(@NotNull Comparable value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.GREATER_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria gte(@NotNull Comparable value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.GREATER_EQUAL_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria lt(@NotNull Comparable value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.LESS_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria lte(@NotNull Comparable value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.LESS_EQUAL_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria in(@NotEmpty Object... values) {
        Assert.isEmpty(values, "values is null");
        return in(Arrays.asList(values));
    }

    @Override
    default Criteria in(@NotEmpty Collection<Object> values) {
        Assert.isEmpty(values, "values is null");
        return Criteria.where(
                CollectionCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.IN)
                        .value(values)
                        .build()
        );
    }

    @Override
    default Criteria nin(@NotEmpty Object... values) {
        Assert.isEmpty(values, "values is null");
        return nin(Arrays.asList(values));
    }

    @Override
    default Criteria nin(@NotEmpty Collection<Object> values) {
        Assert.isEmpty(values, "values is null");
        return Criteria.where(
                CollectionCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_IN)
                        .value(values)
                        .build()
        );
    }

    @Override
    default Criteria isNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NULL)
                        .build()
        );
    }

    @Override
    default Criteria nonNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_NULL)
                        .build()
        );
    }

    @Override
    default Criteria startWith(@NotEmpty String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.START_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notStartWith(@NotEmpty String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_START_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria endWith(@NotEmpty String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.END_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notEndWith(@NotEmpty String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_END_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria contains(@NotEmpty String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.CONTAINS)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notContains(@NotEmpty String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_CONTAINS)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria like(@NotBlank String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.LIKE)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria notLike(@NotBlank String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_LIKE)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria before(@NotNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.BEFORE)
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria before(@NotNull long date) {
        Assert.isNull(date, "date is null");
        return before(new Date(date));
    }

    @Override
    default Criteria after(@NotNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.AFTER)
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria after(@NotNull long date) {
        Assert.isNull(date, "date is null");
        return after(new Date(date));
    }

    @Override
    default Criteria dateEqual(@NotNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.DATE_EQUAL)
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria notDateEqual(@NotNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.NOT_DATE_EQUAL)
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria dateEqual(@NotNull long date) {
        return dateEqual(new Date(date));
    }

    @Override
    default Criteria notDateEqual(@NotNull long date) {
        return notDateEqual(new Date(date));
    }

    @Override
    default Criteria dateBefore(@NotNull Date date) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.DATE_BEFORE)
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria dateBefore(@NotNull long date) {
        Assert.isNull(date, "date is empty");
        return dateBefore(new Date(date));
    }

    @Override
    default Criteria dateAfter(@NotNull Date date) {
        Assert.isNull(date, "date is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(CriterionOperators.DATE_AFTER)
                        .value(date)
                        .build()
        );
    }

    @Override
    default Criteria dateAfter(@NotNull long date) {
        Assert.isNull(date, "date is empty");
        return dateAfter(new Date(date));
    }

    @Override
    default <E extends Comparable<E>> Criteria between(@NotNull E min, @NotNull E max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final BetweenCriterion.Builder<E> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operator(CriterionOperators.BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default <E extends Comparable<E>> Criteria notBetween(@NotNull E min, @NotNull E max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final BetweenCriterion.Builder<E> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operator(CriterionOperators.NOT_BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default Criteria dateBetween(@NotNull Date min, @NotNull Date max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final BetweenCriterion.Builder<Date> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operator(CriterionOperators.DATE_BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default Criteria notDateBetween(@NotNull Date min, @NotNull Date max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final BetweenCriterion.Builder<Date> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operator(CriterionOperators.NOT_DATE_BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default Criteria dateBetween(@NotNull long min, @NotNull long max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        return dateBetween(new Date(min), new Date(max));
    }

    @Override
    default Criteria notDateBetween(@NotNull long min, @NotNull long max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
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
