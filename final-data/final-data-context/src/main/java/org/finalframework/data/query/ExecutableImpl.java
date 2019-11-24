package org.finalframework.data.query;


import org.finalframework.core.Assert;
import org.finalframework.data.query.condition.DateCondition;
import org.finalframework.data.query.criterion.BetweenCriterion;
import org.finalframework.data.query.criterion.CollectionCriterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.finalframework.data.query.function.SimpleFunctionCriterion;
import org.finalframework.data.query.function.SingleFunctionCriterion;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:13:53
 * @since 1.0
 */
public class ExecutableImpl<T> implements Executable<T> {
    private final QProperty<T> property;
    private final Collection<FunctionCriterion> functions = new ArrayList<>();

    public ExecutableImpl(QProperty<T> property) {
        this.property = property;
    }

    @Override
    public DateCondition<T, Criterion> date() {
        return DateCondition.date(property);
    }

    @Override
    public Executable<T> and(T value) {
        this.functions.add(new SingleFunctionCriterion<>(DefaultFunctionOperator.AND, value));
        return this;
    }

    @Override
    public Executable<T> or(T value) {
        this.functions.add(new SingleFunctionCriterion<>(DefaultFunctionOperator.OR, value));
        return this;
    }

    @Override
    public Executable<T> xor(T value) {
        this.functions.add(new SingleFunctionCriterion<>(DefaultFunctionOperator.XOR, value));
        return this;
    }

    @Override
    public Executable<T> not() {
        this.functions.add(new SimpleFunctionCriterion(DefaultFunctionOperator.NOT));
        return this;
    }

    @Override
    public Criterion eq(@NonNull Object value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.EQUAL)
                .value(value)
                .build();
    }

    @Override
    public Criterion neq(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_EQUAL)
                .value(value)
                .build();
    }

    @Override
    public Criterion gt(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.GREATER_THAN)
                .value(value)
                .build();
    }

    @Override
    public Criterion gte(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.GREATER_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    public Criterion lt(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.LESS_THAN)
                .value(value)
                .build();
    }

    @Override
    public Criterion lte(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.LESS_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    public Criterion in(@NonNull T... values) {
        Assert.isEmpty(values, "values is null");
        return in(Arrays.asList(values));
    }

    @Override
    public Criterion in(@NonNull Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return builder.property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.IN)
                .value(values)
                .build();
    }

    @Override
    public Criterion nin(@NonNull T... values) {
        Assert.isEmpty(values, "values is null");
        return nin(Arrays.asList(values));
    }

    @Override
    public Criterion nin(@NonNull Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return builder
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_IN)
                .value(values)
                .build();
    }

    @Override
    public Criterion isNull() {
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NULL)
                .build();
    }

    @Override
    public Criterion isNotNull() {
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_NULL)
                .build();
    }

    @Override
    public Criterion startWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.START_WITH)
                .value(value)
                .build();
    }

    @Override
    public Criterion notStartWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_START_WITH)
                .value(value)
                .build();
    }

    @Override
    public Criterion endWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.END_WITH)
                .value(value)
                .build();
    }

    @Override
    public Criterion notEndWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_END_WITH)
                .value(value)
                .build();
    }

    @Override
    public Criterion contains(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.CONTAINS)
                .value(value)
                .build();
    }

    @Override
    public Criterion notContains(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_CONTAINS)
                .value(value)
                .build();
    }

    @Override
    public Criterion like(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.LIKE)
                .value(value)
                .build();
    }

    @Override
    public Criterion notLike(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_LIKE)
                .value(value)
                .build();
    }

    @Override
    public Criterion dateEqual(LocalDateTime date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_EQUAL)
                .value(date)
                .build();
    }

    @Override
    public Criterion dateEqual(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_EQUAL)
                .value(date)
                .build();
    }

    @Override
    public Criterion notDateEqual(LocalDateTime date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_DATE_EQUAL)
                .value(date)
                .build();
    }

    @Override
    public Criterion notDateEqual(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_DATE_EQUAL)
                .value(date)
                .build();
    }

    @Override
    public Criterion dateEqual(@NonNull long date) {
        return dateEqual(new Date(date));
    }

    @Override
    public Criterion notDateEqual(@NonNull long date) {
        return notDateEqual(new Date(date));
    }

    @Override
    public Criterion dateBefore(LocalDateTime date) {
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_BEFORE)
                .value(date)
                .build();
    }

    @Override
    public Criterion dateBefore(@NonNull Date date) {
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_BEFORE)
                .value(date)
                .build();
    }

    @Override
    public Criterion dateBefore(@NonNull long date) {
        Assert.isNull(date, "date is null");
        return dateBefore(new Date(date));
    }

    @Override
    public Criterion dateAfter(LocalDateTime date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_AFTER)
                .value(date)
                .build();
    }

    @Override
    public Criterion dateAfter(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_AFTER)
                .value(date)
                .build();
    }

    @Override
    public Criterion dateAfter(@NonNull long date) {
        Assert.isNull(date, "date is null");
        return dateAfter(new Date(date));
    }

    @Override
    public Criterion between(@NonNull T min, @NonNull T max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        final BetweenCriterion.Builder<T> builder = BetweenCriterion.builder();
        return builder.property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    public Criterion notBetween(@NonNull T min, @NonNull T max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        final BetweenCriterion.Builder<T> builder = BetweenCriterion.builder();
        return builder.property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    public Criterion dateBetween(LocalDateTime min, LocalDateTime max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        final BetweenCriterion.Builder<LocalDateTime> builder = BetweenCriterion.builder();
        return builder.property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    public Criterion dateBetween(@NonNull Date min, @NonNull Date max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        final BetweenCriterion.Builder<Date> builder = BetweenCriterion.builder();
        return builder.property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.DATE_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    public Criterion notDateBetween(LocalDateTime min, LocalDateTime max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        final BetweenCriterion.Builder<LocalDateTime> builder = BetweenCriterion.builder();
        return builder.property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_DATE_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    public Criterion notDateBetween(@NonNull Date min, @NonNull Date max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        final BetweenCriterion.Builder<Date> builder = BetweenCriterion.builder();
        return builder.property(property)
                .function(functions)
                .operator(DefaultCriterionOperator.NOT_DATE_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    public Criterion dateBetween(@NonNull long min, @NonNull long max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        return dateBetween(new Date(min), new Date(max));
    }

    @Override
    public Criterion notDateBetween(@NonNull long min, @NonNull long max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        return notDateBetween(new Date(min), new Date(max));
    }
}
