package org.finalframework.data.query.criteriable;


import org.finalframework.core.Assert;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.BetweenCriterion;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;
import org.finalframework.data.query.operation.*;

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
                .property(this.property, this.functions)
                .operation(CompareOperation.NULL)
                .build();
    }

    @Override
    public Criterion isNotNull() {
        return SingleCriterion.builder()
                .property(this.property, this.functions)
                .operation(CompareOperation.NOT_NULL)
                .build();
    }

    @Override
    public Criterion between(V min, V max) {
        return buildBetweenCriterion(CompareOperation.BETWEEN, min, max);
    }

    @Override
    public Criterion notBetween(V min, V max) {
        return buildBetweenCriterion(CompareOperation.NOT_BETWEEN, min, max);
    }

    @Override
    public Criterion eq(V value) {
        return buildSingleCriterion(CompareOperation.EQUAL, value);
    }

    @Override
    public Criterion neq(V value) {
        return buildSingleCriterion(CompareOperation.NOT_EQUAL, value);
    }

    @Override
    public Criterion gt(V value) {
        return buildSingleCriterion(CompareOperation.GREAT_THAN, value);
    }

    @Override
    public Criterion gte(V value) {
        return buildSingleCriterion(CompareOperation.GREAT_THAN_EQUAL, value);
    }

    @Override
    public Criterion lt(V value) {
        return buildSingleCriterion(CompareOperation.LESS_THAN, value);
    }

    @Override
    public Criterion lte(V value) {
        return buildSingleCriterion(CompareOperation.LESS_THAN_EQUAL, value);
    }

    @Override
    public Criterion in(Collection<V> values) {
        return buildSingleCriterion(CompareOperation.IN, values);
    }

    @Override
    public Criterion nin(Collection<V> values) {
        return buildSingleCriterion(CompareOperation.NOT_IN, values);
    }

    @Override
    public Criterion like(String prefix, String value, String suffix) {
        return buildLikeCriterion(CompareOperation.LIKE, prefix, value, suffix);
    }

    @Override
    public Criterion notLike(String prefix, String value, String suffix) {
        return buildLikeCriterion(CompareOperation.NOT_LIKE, prefix, value, suffix);
    }

    private Criterion buildSingleCriterion(Operation operation, Object value) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this.property, this.functions)
                .operation(operation)
                .value(value)
                .build();
    }

    private Criterion buildLikeCriterion(Operation operation, String prefix, String value, String suffix) {
        Assert.isNull(value, "value is null");
        return SingleCriterion.builder()
                .property(this.property, this.functions)
                .operation(operation)
                .value(value)
                .build().contact(prefix, suffix);
    }

    private Criterion buildBetweenCriterion(Operation operation, Object min, Object max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        return BetweenCriterion.builder()
                .property(this.property, this.functions)
                .operation(operation)
                .between(min, max)
                .build();
    }


    @Override
    public FunctionCriteriable<V, Criterion> jsonExtract(String path) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(JsonOperation.JSON_EXTRACT, path));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> jsonKeys() {
        this.addFunctionCriterion(new SimpleFunctionOperation(JsonOperation.JSON_KEYS));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> jsonLength() {
        this.addFunctionCriterion(new SimpleFunctionOperation(JsonOperation.JSON_LENGTH));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> jsonDepth() {
        this.addFunctionCriterion(new SimpleFunctionOperation(JsonOperation.JSON_DEPTH));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> jsonUnquote() {
        this.addFunctionCriterion(new SimpleFunctionOperation(JsonOperation.JSON_UNQUOTE));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> and(V value) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(LogicOperation.AND, value));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> or(V value) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(LogicOperation.OR, value));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> xor(V value) {
        this.addFunctionCriterion(new SingleFunctionOperation<>(LogicOperation.XOR, value));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> not() {
        this.addFunctionCriterion(new SimpleFunctionOperation(LogicOperation.NOT));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> date() {
        this.addFunctionCriterion(new SimpleFunctionOperation(DateOperation.DATE));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> min() {
        this.addFunctionCriterion(new SimpleFunctionOperation(MathOperation.MIN));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> max() {
        this.addFunctionCriterion(new SimpleFunctionOperation(MathOperation.MAX));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> sum() {
        this.addFunctionCriterion(new SimpleFunctionOperation(MathOperation.SUM));
        return this;
    }

    @Override
    public FunctionCriteriable<V, Criterion> avg() {
        this.addFunctionCriterion(new SimpleFunctionOperation(MathOperation.AVG));
        return this;
    }


    @Override
    public Criterion jsonContains(@NotNull Object value, String path) {
        this.addFunctionCriterion(new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path));
        return buildSingleCriterion(CompareOperation.EQUAL, true);
    }

    @Override
    public Criterion notJsonContains(Object value, String path) {
        this.addFunctionCriterion(new DoubleFunctionOperation<>(JsonOperation.JSON_CONTAINS, value, path));
        return buildSingleCriterion(CompareOperation.NOT_EQUAL, true);
    }

}

