package org.finalframework.data.query.criteriable;


import org.finalframework.core.Assert;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.CriterionOperator;
import org.finalframework.data.query.criterion.*;
import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 14:59:59
 * @since 1.0
 */
public class AbsCriteriable<T, V> implements Criteriable<V, Criterion>, FunctionCriteriable<V, Criterion> {
    private final QProperty<T> property;
    private final Collection<FunctionOperation> functions = new ArrayList<>();

    public AbsCriteriable(QProperty<T> property) {
        this.property = property;
    }

    public AbsCriteriable(QProperty<T> property, FunctionOperation function) {
        this(property);
        this.addFunctionCriterion(function);
    }

    public AbsCriteriable(QProperty<T> property, Collection<FunctionOperation> functions) {
        this.property = property;
        this.functions.addAll(functions);
    }

    protected void addFunctionCriterion(FunctionOperation function) {
        this.functions.add(function);
    }

    @Override
    public Criterion isNull() {
        return SingleCriterion.builder()
                .property(this.property)
                .operator(CriterionOperator.NULL)
                .build();
    }

    @Override
    public Criterion isNotNull() {
        return SingleCriterion.builder()
                .property(this.property)
                .operator(CriterionOperator.NOT_NULL)
                .build();
    }

    @Override
    public Criterion between(V min, V max) {
        return buildBetweenCriterion(CriterionOperator.BETWEEN, min, max);
    }

    @Override
    public Criterion notBetween(V min, V max) {
        return buildBetweenCriterion(CriterionOperator.NOT_BETWEEN, min, max);
    }

    @Override
    public Criterion eq(V value) {
        return buildSingleCriterion(CriterionOperator.EQUAL, value);
    }

    @Override
    public Criterion neq(V value) {
        return buildSingleCriterion(CriterionOperator.NOT_EQUAL, value);
    }

    @Override
    public Criterion gt(V value) {
        return buildSingleCriterion(CriterionOperator.GREAT_THAN, value);
    }

    @Override
    public Criterion gte(V value) {
        return buildSingleCriterion(CriterionOperator.GREAT_THAN_EQUAL, value);
    }

    @Override
    public Criterion lt(V value) {
        return buildSingleCriterion(CriterionOperator.LESS_THAN, value);
    }

    @Override
    public Criterion lte(V value) {
        return buildSingleCriterion(CriterionOperator.LESS_THAN_EQUAL, value);
    }

    @Override
    public Criterion in(Collection<V> values) {
        return buildSingleCriterion(CriterionOperator.IN, values);
    }

    @Override
    public Criterion nin(Collection<V> values) {
        return buildSingleCriterion(CriterionOperator.NOT_IN, values);
    }

    @Override
    public Criterion like(String prefix, String value, String suffix) {
        return buildLikeCriterion(CriterionOperator.LIKE, prefix, value, suffix);
    }

    @Override
    public Criterion notLike(String prefix, String value, String suffix) {
        return buildLikeCriterion(CriterionOperator.NOT_LIKE, prefix, value, suffix);
    }

    private Criterion buildSingleCriterion(CriterionOperator operator, Object value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this.property, this.functions)
                .operator(operator)
                .value(value)
                .build();
    }

    private Criterion buildLikeCriterion(CriterionOperator operator, String prefix, String value, String suffix) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this.property, this.functions)
                .operator(operator)
                .value(value)
                .build().contact(prefix, suffix);
    }

    private Criterion buildBetweenCriterion(CriterionOperator operator, Object min, Object max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        return BetweenCriterion.builder()
                .property(this.property, this.functions)
                .operator(operator)
                .between(min, max)
                .build();
    }


    @Override
    public FunctionCriteriable<V, Criterion> jsonExtract(String path) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(FunctionOperator.JSON_EXTRACT, path));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> jsonContains(@NotNull Object value, String path) {
        this.addFunctionCriterion(new DoubleFunctionOperation<>(FunctionOperator.JSON_CONTAINS, value, path));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> jsonUnquote() {
        this.addFunctionCriterion(new SimpleFunctionOperation(FunctionOperator.JSON_UNQUOTE));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> and(V value) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(FunctionOperator.AND, value));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> or(V value) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(FunctionOperator.OR, value));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> xor(V value) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(FunctionOperator.XOR, value));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> not() {
        this.addFunctionCriterion(new SimpleFunctionOperation(FunctionOperator.NOT));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> date() {
        this.addFunctionCriterion(new SimpleFunctionOperation(FunctionOperator.DATE));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> min() {
        this.addFunctionCriterion(new SimpleFunctionOperation(FunctionOperator.MIN));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> max() {
        this.addFunctionCriterion(new SimpleFunctionOperation(FunctionOperator.MAX));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> sum() {
        this.addFunctionCriterion(new SimpleFunctionOperation(FunctionOperator.SUM));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> avg() {
        this.addFunctionCriterion(new SimpleFunctionOperation(FunctionOperator.AVG));
        return this;
    }
}

