package org.finalframework.data.query;


import org.finalframework.core.Assert;
import org.finalframework.data.query.criterion.CollectionCriterion;
import org.finalframework.data.query.criterion.DoubleCriterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.finalframework.data.query.function.SimpleFunctionCriterion;
import org.finalframework.data.query.function.SingleFunctionCriterion;
import org.springframework.lang.NonNull;

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
    public Executable<T> date() {
        this.functions.add(new SimpleFunctionCriterion(DefaultFunctionOperator.DATE));
        return this;
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
    public Criteria eq(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria neq(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria gt(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.GREATER_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria gte(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.GREATER_THAN_EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria lt(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.LESS_THAN)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria lte(@NonNull T value) {
        Assert.isNull(value, "value is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.LESS_THAN_EQUAL)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria in(@NonNull T... values) {
        Assert.isEmpty(values, "values is null");
        return in(Arrays.asList(values));
    }

    @Override
    public Criteria in(@NonNull Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return Criteria.where(
                builder
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.IN)
                        .value(values)
                        .build()
        );
    }

    @Override
    public Criteria nin(@NonNull T... values) {
        Assert.isEmpty(values, "values is null");
        return nin(Arrays.asList(values));
    }

    @Override
    public Criteria nin(@NonNull Collection<T> values) {
        Assert.isEmpty(values, "values is null");
        final CollectionCriterion.Builder<T> builder = CollectionCriterion.builder();
        return Criteria.where(
                builder
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_IN)
                        .value(values)
                        .build()
        );
    }

    @Override
    public Criteria isNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NULL)
                        .build()
        );
    }

    @Override
    public Criteria nonNull() {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_NULL)
                        .build()
        );
    }

    @Override
    public Criteria startWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.START_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria notStartWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_START_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria endWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.END_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria notEndWith(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_END_WITH)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria contains(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.CONTAINS)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria notContains(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_CONTAINS)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria like(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.LIKE)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria notLike(@NonNull String value) {
        Assert.isBlank(value, "value is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_LIKE)
                        .value(value)
                        .build()
        );
    }

    @Override
    public Criteria before(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.BEFORE)
                        .value(date)
                        .build()
        );
    }

    @Override
    public Criteria before(@NonNull long date) {
        Assert.isNull(date, "date is null");
        return before(new Date(date));
    }

    @Override
    public Criteria after(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.AFTER)
                        .value(date)
                        .build()
        );
    }

    @Override
    public Criteria after(@NonNull long date) {
        Assert.isNull(date, "date is null");
        return after(new Date(date));
    }

    @Override
    public Criteria dateEqual(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.DATE_EQUAL)
                        .value(date)
                        .build()
        );
    }

    @Override
    public Criteria notDateEqual(@NonNull Date date) {
        Assert.isNull(date, "date is null");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_DATE_EQUAL)
                        .value(date)
                        .build()
        );
    }

    @Override
    public Criteria dateEqual(@NonNull long date) {
        return dateEqual(new Date(date));
    }

    @Override
    public Criteria notDateEqual(@NonNull long date) {
        return notDateEqual(new Date(date));
    }

    @Override
    public Criteria dateBefore(@NonNull Date date) {
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.DATE_BEFORE)
                        .value(date)
                        .build()
        );
    }

    @Override
    public Criteria dateBefore(@NonNull long date) {
        Assert.isNull(date, "date is empty");
        return dateBefore(new Date(date));
    }

    @Override
    public Criteria dateAfter(@NonNull Date date) {
        Assert.isNull(date, "date is empty");
        return Criteria.where(
                SingleCriterion.builder()
                        .property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.DATE_AFTER)
                        .value(date)
                        .build()
        );
    }

    @Override
    public Criteria dateAfter(@NonNull long date) {
        Assert.isNull(date, "date is empty");
        return dateAfter(new Date(date));
    }

    @Override
    public Criteria between(@NonNull T min, @NonNull T max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<T> builder = DoubleCriterion.builder();
        return Criteria.where(
                builder.property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    public Criteria notBetween(@NonNull T min, @NonNull T max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<T> builder = DoubleCriterion.builder();
        return Criteria.where(
                builder.property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    public Criteria dateBetween(@NonNull Date min, @NonNull Date max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<Date> builder = DoubleCriterion.builder();
        return Criteria.where(
                builder.property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.DATE_BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    public Criteria notDateBetween(@NonNull Date min, @NonNull Date max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        final DoubleCriterion.Builder<Date> builder = DoubleCriterion.builder();
        return Criteria.where(
                builder.property(property)
                        .function(functions)
                        .operator(DefaultCriterionOperator.NOT_DATE_BETWEEN)
                        .between(min, max)
                        .build()
        );
    }

    @Override
    public Criteria dateBetween(@NonNull long min, @NonNull long max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        return dateBetween(new Date(min), new Date(max));
    }

    @Override
    public Criteria notDateBetween(@NonNull long min, @NonNull long max) {
        Assert.isNull(min, "min is empty");
        Assert.isNull(max, "max is empty");
        return notDateBetween(new Date(min), new Date(max));
    }
}
