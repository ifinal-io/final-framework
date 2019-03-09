package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import com.ilikly.finalframework.data.query.criterion.BetweenCriterion;
import com.ilikly.finalframework.data.query.criterion.CollectionCriterion;
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
public interface QProperty<T> extends Criteriable<T, Criteria>, Sortable<Sort> {

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
    default Criteria eq(@NotNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria neq(@NotNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria gt(@NotNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.GREATER_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria gte(@NotNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.GREATER_THAN_EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria lt(@NotNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.LESS_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria lte(@NotNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.LESS_THAN_EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    default Criteria in(@NotEmpty T... values) {
        Assert.isEmpty(values, "values is null");
        return in(Arrays.asList(values));
    }

    @Override
    default Criteria in(@NotEmpty Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return Criteria.where(
                builder
                        .property(this)
                        .operator(DefaultCriterionOperator.IN)
                        .value(values)
                        .build()
        );
    }

    @Override
    default Criteria nin(@NotEmpty T... values) {
        Assert.isEmpty(values, "values is null");
        return nin(Arrays.asList(values));
    }

    @Override
    default Criteria nin(@NotEmpty Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return Criteria.where(
                builder
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_IN)
                        .value(values)
                        .build()
        );
    }

    @Override
    default Criteria isNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NULL)
                        .build()
        );
    }

    @Override
    default Criteria nonNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_NULL)
                        .build()
        );
    }

    @Override
    default Criteria startWith(@NotEmpty String value) {
        Assert.isEmpty(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.START_WITH)
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
                        .operator(DefaultCriterionOperator.NOT_START_WITH)
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
                        .operator(DefaultCriterionOperator.END_WITH)
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
                        .operator(DefaultCriterionOperator.NOT_END_WITH)
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
                        .operator(DefaultCriterionOperator.CONTAINS)
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
                        .operator(DefaultCriterionOperator.NOT_CONTAINS)
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
                        .operator(DefaultCriterionOperator.LIKE)
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
                        .operator(DefaultCriterionOperator.NOT_LIKE)
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
                        .operator(DefaultCriterionOperator.BEFORE)
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
                        .operator(DefaultCriterionOperator.AFTER)
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
                        .operator(DefaultCriterionOperator.DATE_EQUAL)
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
                        .operator(DefaultCriterionOperator.NOT_DATE_EQUAL)
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
                        .operator(DefaultCriterionOperator.DATE_BEFORE)
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
                        .operator(DefaultCriterionOperator.DATE_AFTER)
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
    default Criteria between(@NotNull T min, @NotNull T max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final BetweenCriterion.Builder<T> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operator(DefaultCriterionOperator.BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    default Criteria notBetween(@NotNull T min, @NotNull T max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final BetweenCriterion.Builder<T> builder = BetweenCriterion.builder();
        return Criteria.where(
                builder.property(this)
                        .operator(DefaultCriterionOperator.NOT_BETWEEN)
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
                        .operator(DefaultCriterionOperator.DATE_BETWEEN)
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
                        .operator(DefaultCriterionOperator.NOT_DATE_BETWEEN)
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
