package org.finalframework.data.query.criteriable;


import org.finalframework.core.Assert;
import org.finalframework.data.query.*;
import org.finalframework.data.query.condition.BetweenCondition;
import org.finalframework.data.query.condition.CompareCondition;
import org.finalframework.data.query.condition.InCondition;
import org.finalframework.data.query.condition.LikeCondition;
import org.finalframework.data.query.criterion.*;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.criterion.operator.DefaultCriterionOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 14:59:59
 * @since 1.0
 */
public class AbsCriteriable<T, V> implements CompareCondition<V, Criterion>, BetweenCondition<V, Criterion>,
        InCondition<V, Criterion>, LikeCondition<Criterion> {
    private final QProperty<T> property;
    private final Collection<FunctionCriterion> functions = new ArrayList<>();
    public AbsCriteriable(QProperty<T> property) {
        this.property = property;
    }

    public AbsCriteriable(QProperty<T> property, FunctionCriterion functions) {
        this(property, Arrays.asList(functions));
    }

    public AbsCriteriable(QProperty<T> property, Collection<FunctionCriterion> functions) {
        this.property = property;
        this.functions.addAll(functions);
    }

    protected void addFunctionCriterion(FunctionCriterion function) {
        this.functions.add(function);
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
                .property(this.property == null ? (QProperty) this : this.property)
                .function(this.functions)
                .operator(operator)
                .value(value)
                .build();
    }

    private Criterion buildLikeCriterion(CriterionOperator operator, String prefix, String value, String suffix) {
        Assert.isNull(value, "value is null");
        return LikeCriterion.builder()
                .property(this.property == null ? (QProperty) this : this.property)
                .function(this.functions)
                .operator(operator)
                .like(prefix, value, suffix)
                .build();
    }

    private Criterion buildBetweenCriterion(CriterionOperator operator, Object min, Object max) {
        Assert.isNull(min, "min is null");
        Assert.isNull(max, "max is null");
        return BetweenCriterion.builder()
                .property(this.property == null ? (QProperty) this : this.property)
                .function(this.functions)
                .operator(operator)
                .between(min, max)
                .build();
    }
}

