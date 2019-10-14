package org.finalframework.data.query;

import org.finalframework.core.Assert;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.query.criterion.CollectionCriterion;
import org.finalframework.data.query.criterion.DoubleCriterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.springframework.lang.NonNull;

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
public interface QProperty<T> extends Criteriable<T, Criterion>, Sortable<Sort>, Executable<T> {

    Class<T> getType();

    Class getComponentType();

    String getPath();

    @NonNull
    String getTable();

    @NonNull
    String getName();

    @NonNull
    String getColumn();

    @NonNull
    PersistentType getPersistentType();

    boolean isEntity();

    boolean isIdProperty();

    boolean hasView(@NonNull Class<?> view);

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
    default Criterion eq(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this)
                .operator(DefaultCriterionOperator.EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion neq(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_EQUAL)
                        .value(value)
                        .build();
    }

    @Override
    default Criterion gt(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.GREATER_THAN)
                        .value(value)
                        .build();
    }

    @Override
    default Criterion gte(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return
                SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.GREATER_THAN_EQUAL)
                        .value(value)
                        .build();
    }

    @Override
    default Criterion lt(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this)
                .operator(DefaultCriterionOperator.LESS_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion lte(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this)
                .operator(DefaultCriterionOperator.LESS_THAN_EQUAL)
                .value(value)
                .build()
                ;
    }

    @Override
    default Criterion in(@NonNull T... values) {
        Assert.isEmpty(values, "values is null");
        return in(Arrays.asList(values));
    }

    @Override
    default Criterion in(@NonNull Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return builder.property(this)
                .operator(DefaultCriterionOperator.IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion nin(@NonNull T... values) {
        Assert.isEmpty(values, "values is null");
        return nin(Arrays.asList(values));
    }

    @Override
    default Criterion nin(@NonNull Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return builder.property(this)
                .operator(DefaultCriterionOperator.NOT_IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion isNull() {
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NULL)
                .build();
    }

    @Override
    default Criterion nonNull() {
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_NULL)
                .build();
    }

    @Override
    default Criterion startWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.START_WITH)
                        .value(value)
                .build();
    }

    @Override
    default Criterion notStartWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_START_WITH)
                        .value(value)
                .build();
    }

    @Override
    default Criterion endWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.END_WITH)
                        .value(value)
                .build();
    }

    @Override
    default Criterion notEndWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_END_WITH)
                        .value(value)
                .build();
    }

    @Override
    default Criterion contains(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.CONTAINS)
                        .value(value)
                .build();
    }

    @Override
    default Criterion notContains(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_CONTAINS)
                        .value(value)
                .build();
    }

    @Override
    default Criterion like(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.LIKE)
                        .value(value)
                .build();
    }

    @Override
    default Criterion notLike(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_LIKE)
                        .value(value)
                .build();
    }

    @Override
    default Criterion before(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.BEFORE)
                        .value(date)
                .build();
    }

    @Override
    default Criterion before(@NonNull long date) {
        Assert.isNull(date, "date is null");
        return before(new Date(date));
    }

    @Override
    default Criterion after(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.AFTER)
                        .value(date)
                .build();
    }

    @Override
    default Criterion after(@NonNull long date) {
        Assert.isNull(date, "date is null");
        return after(new Date(date));
    }

    @Override
    default Criterion dateEqual(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.DATE_EQUAL)
                        .value(date)
                .build();
    }

    @Override
    default Criterion notDateEqual(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.NOT_DATE_EQUAL)
                        .value(date)
                .build();
    }

    @Override
    default Criterion dateEqual(@NonNull long date) {
        return dateEqual(new Date(date));
    }

    @Override
    default Criterion notDateEqual(@NonNull long date) {
        return notDateEqual(new Date(date));
    }

    @Override
    default Criterion dateBefore(@NonNull Date date) {
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.DATE_BEFORE)
                        .value(date)
                .build();
    }

    @Override
    default Criterion dateBefore(@NonNull long date) {
        Assert.isNull(date, "date is empty");
        return dateBefore(new Date(date));
    }

    @Override
    default Criterion dateAfter(@NonNull Date date) {
        Assert.isNull(date, "date is empty");
        return SingleCriterion.builder()
                        .property(this)
                        .operator(DefaultCriterionOperator.DATE_AFTER)
                        .value(date)
                .build();
    }

    @Override
    default Criterion dateAfter(@NonNull long date) {
        Assert.isNull(date, "date is empty");
        return dateAfter(new Date(date));
    }

    @Override
    default Criterion between(@NonNull T min, @NonNull T max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<T> builder = DoubleCriterion.builder();
        return builder.property(this)
                        .operator(DefaultCriterionOperator.BETWEEN)
                        .between(min, max)
                .build();
    }

    @Override
    default Criterion notBetween(@NonNull T min, @NonNull T max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<T> builder = DoubleCriterion.builder();
        return builder.property(this)
                        .operator(DefaultCriterionOperator.NOT_BETWEEN)
                        .between(min, max)
                .build();
    }

    @Override
    default Criterion dateBetween(@NonNull Date min, @NonNull Date max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<Date> builder = DoubleCriterion.builder();
        return builder.property(this)
                        .operator(DefaultCriterionOperator.DATE_BETWEEN)
                        .between(min, max)
                .build();
    }

    @Override
    default Criterion notDateBetween(@NonNull Date min, @NonNull Date max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<Date> builder = DoubleCriterion.builder();
        return builder.property(this)
                        .operator(DefaultCriterionOperator.NOT_DATE_BETWEEN)
                        .between(min, max)
                .build();
    }

    @Override
    default Criterion dateBetween(@NonNull long min, @NonNull long max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        return dateBetween(new Date(min), new Date(max));
    }

    @Override
    default Criterion notDateBetween(@NonNull long min, @NonNull long max) {
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

    @Override
    default Executable<T> date() {
        return Executable.execute(this).date();
    }

    @Override
    default Executable<T> and(T value) {
        return Executable.execute(this).and(value);
    }

    @Override
    default Executable<T> or(T value) {
        return Executable.execute(this).or(value);
    }

    @Override
    default Executable<T> xor(T value) {
        return Executable.execute(this).xor(value);
    }

    @Override
    default Executable<T> not() {
        return Executable.execute(this).not();
    }
}
