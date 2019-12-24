package org.finalframework.data.query.criteriable;


import org.finalframework.core.Assert;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.*;
import org.finalframework.data.query.criterion.function.SimpleFunctionCriterion;
import org.finalframework.data.query.criterion.function.SingleFunctionCriterion;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.criterion.operator.DefaultCriterionOperator;

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
    private final Collection<FunctionCriterion> functions = new ArrayList<>();

    public AbsCriteriable(QProperty<T> property) {
        this.property = property;
    }

    public AbsCriteriable(QProperty<T> property, FunctionCriterion function) {
        this(property);
        this.addFunctionCriterion(function);
    }

    public AbsCriteriable(QProperty<T> property, Collection<FunctionCriterion> functions) {
        this.property = property;
        this.functions.addAll(functions);
    }

    protected void addFunctionCriterion(FunctionCriterion function) {
        this.functions.add(function);
    }

    @Override
    public Criterion isNull() {
        return SingleCriterion.builder()
                .property(this.property)
                .operator(DefaultCriterionOperator.NULL)
                .build();
    }

    @Override
    public Criterion isNotNull() {
        return SingleCriterion.builder()
                .property(this.property)
                .operator(DefaultCriterionOperator.NOT_NULL)
                .build();
    }

    @Override
    public Criterion between(V min, V max) {
        return buildBetweenCriterion(DefaultCriterionOperator.BETWEEN, min, max);
    }

    @Override
    public Criterion notBetween(V min, V max) {
        return buildBetweenCriterion(DefaultCriterionOperator.NOT_BETWEEN, min, max);
    }

    @Override
    public Criterion eq(V value) {
        return buildSingleCriterion(DefaultCriterionOperator.EQUAL, value);
    }

    @Override
    public Criterion neq(V value) {
        return buildSingleCriterion(DefaultCriterionOperator.NOT_EQUAL, value);
    }

    @Override
    public Criterion gt(V value) {
        return buildSingleCriterion(DefaultCriterionOperator.GREATER_THAN, value);
    }

    @Override
    public Criterion gte(V value) {
        return buildSingleCriterion(DefaultCriterionOperator.GREATER_THAN_EQUAL, value);
    }

    @Override
    public Criterion lt(V value) {
        return buildSingleCriterion(DefaultCriterionOperator.LESS_THAN, value);
    }

    @Override
    public Criterion lte(V value) {
        return buildSingleCriterion(DefaultCriterionOperator.LESS_THAN_EQUAL, value);
    }

    @Override
    public Criterion in(Collection<V> values) {
        return buildSingleCriterion(DefaultCriterionOperator.IN, values);
    }

    @Override
    public Criterion nin(Collection<V> values) {
        return buildSingleCriterion(DefaultCriterionOperator.NOT_IN, values);
    }

    @Override
    public Criterion like(String prefix, String value, String suffix) {
        return buildLikeCriterion(DefaultCriterionOperator.LIKE, prefix, value, suffix);
    }

    @Override
    public Criterion notLike(String prefix, String value, String suffix) {
        return buildLikeCriterion(DefaultCriterionOperator.NOT_LIKE, prefix, value, suffix);
    }

    private Criterion buildSingleCriterion(CriterionOperator operator, Object value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this.property)
                .function(this.functions)
                .operator(operator)
                .value(value)
                .build();
    }

    private Criterion buildLikeCriterion(CriterionOperator operator, String prefix, String value, String suffix) {
        Assert.isNull(value, "value is null");
        return LikeCriterion.builder()
                .property(this.property)
                .function(this.functions)
                .operator(operator)
                .like(prefix, value, suffix)
                .build();
    }

    private Criterion buildBetweenCriterion(CriterionOperator operator, Object min, Object max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        return BetweenCriterion.builder()
                .property(this.property)
                .function(this.functions)
                .operator(operator)
                .between(min, max)
                .build();
    }

    @Override
    public DateCriteriable<Criterion> date() {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SimpleFunctionCriterion(FunctionOperation.DATE));
        return new DateCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> min() {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SimpleFunctionCriterion(FunctionOperation.MIN));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> max() {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SimpleFunctionCriterion(FunctionOperation.MAX));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> sum() {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SimpleFunctionCriterion(FunctionOperation.SUM));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> avg() {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SimpleFunctionCriterion(FunctionOperation.AVG));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> and(V value) {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SingleFunctionCriterion<>(FunctionOperation.AND, value));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> or(V value) {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SingleFunctionCriterion<>(FunctionOperation.OR, value));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> xor(V value) {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SingleFunctionCriterion<>(FunctionOperation.XOR, value));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public NumberCriteriable<Criterion> not() {
        ArrayList<FunctionCriterion> functions = new ArrayList<>(this.functions);
        functions.add(new SimpleFunctionCriterion(FunctionOperation.NOT));
        return new NumberCriteriableImpl<>(this.property, functions);
    }

    @Override
    public FunctionCriteriable<V, Criterion> extract(String path) {
        this.addFunctionCriterion(new SingleFunctionCriterion<>(FunctionOperation.JSON_EXTRACT, path));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> unquote() {
        this.addFunctionCriterion(new SimpleFunctionCriterion(FunctionOperation.JSON_UNQUOTE));
        return this;
    }
}

