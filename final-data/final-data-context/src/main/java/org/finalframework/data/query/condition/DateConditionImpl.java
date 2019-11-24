package org.finalframework.data.query.condition;


import org.finalframework.core.Assert;
import org.finalframework.data.query.*;
import org.finalframework.data.query.criterion.BetweenCriterion;
import org.finalframework.data.query.criterion.CollectionCriterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.finalframework.data.query.function.SimpleFunctionCriterion;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-23 23:44:10
 * @since 1.0
 */
public class DateConditionImpl<T> implements DateCondition<T, Criterion> {
    private final QProperty<T> property;
    private final Collection<FunctionCriterion> functions = new ArrayList<>();

    public DateConditionImpl(QProperty<T> property) {
        this.property = property;
        this.functions.add(new SimpleFunctionCriterion(DefaultFunctionOperator.DATE));
    }

    @Override
    public Criterion eq(@NonNull String value) {
        return date(DefaultCriterionOperator.EQUAL, value);
    }

    @Override
    public Criterion neq(String value) {
        return date(DefaultCriterionOperator.NOT_EQUAL, value);
    }

    @Override
    public Criterion gt(String value) {
        return date(DefaultCriterionOperator.GREATER_THAN, value);
    }

    @Override
    public Criterion gte(String value) {
        return date(DefaultCriterionOperator.GREATER_THAN_EQUAL, value);
    }

    @Override
    public Criterion lt(String value) {
        return date(DefaultCriterionOperator.LESS_THAN, value);
    }

    @Override
    public Criterion lte(String value) {
        return date(DefaultCriterionOperator.LESS_THAN_EQUAL, value);
    }


    @Override
    public Criterion between(String min, String max) {
        return date(DefaultCriterionOperator.BETWEEN, min, max);
    }

    @Override
    public Criterion notBetween(String min, String max) {
        return date(DefaultCriterionOperator.NOT_BETWEEN, min, max);
    }

    @Override
    public Criterion in(Collection<String> values) {
        return date(DefaultCriterionOperator.IN, values);
    }

    @Override
    public Criterion nin(Collection<String> values) {
        return date(DefaultCriterionOperator.NOT_IN, values);
    }

    private Criterion date(CriterionOperator operator, String date) {
        Assert.isNull(date, "date is null");
        return SingleCriterion.builder()
                .property(property)
                .function(functions)
                .operator(operator)
                .value(date)
                .build();
    }

    private Criterion date(CriterionOperator operator, String min, String max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        return BetweenCriterion.builder()
                .property(property)
                .function(functions)
                .operator(operator)
                .between(min, max)
                .build();
    }

    private Criterion date(CriterionOperator operator, Collection<String> values) {
        Assert.isEmpty(values, "values is null");
        CollectionCriterion.Builder<String> builder = CollectionCriterion.builder();
        return builder
                .property(property)
                .function(functions)
                .operator(operator)
                .value(values)
                .build();
    }
}

