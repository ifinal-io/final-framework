package org.ifinal.finalframework.data.query.criterion;

import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.data.query.SqlNode;
import org.ifinal.finalframework.data.query.criteriable.Criteriable;
import org.ifinal.finalframework.data.query.criterion.function.CriterionFunction;
import org.ifinal.finalframework.data.query.operation.Operation.CompareOperation;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionTarget<T> extends Criteriable<Object, Criterion>, SqlNode {

    static <T> CriterionTarget<T> from(T target) {
        return target instanceof CriterionTarget ? (CriterionTarget<T>) target : new CriterionTargetImpl<>(target);
    }

    T getTarget();

    default CriterionTarget<CriterionFunction> apply(Function<T, CriterionFunction> mapper) {
        return from(mapper.apply(getTarget()));
    }

    @Override
    default void apply(StringBuilder parent, String expression) {
        final T target = this.getTarget();
        if (target instanceof SqlNode) {
            ((SqlNode) target).apply(parent, String.format("%s.target", expression));
        }
        if (target instanceof QProperty) {
            parent.append(String.format("${%s.target.column}", expression));
        } else {
            parent.append("${target}");
        }
    }

    @Override
    default Criterion between(Object min, Object max) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.BETWEEN)
                .min(min).max(max)
                .build();

    }

    @Override
    default Criterion notBetween(Object min, Object max) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_BETWEEN)
                .min(min).max(max)
                .build();
    }

    @Override
    default Criterion eq(Object value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion neq(Object value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion gt(Object value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.GREAT_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion gte(Object value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.GREAT_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion lt(Object value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.LESS_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion lte(Object value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.LESS_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion in(Collection<Object> values) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion nin(Collection<Object> values) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion jsonContains(Object value, String path) {
        return JsonContainsCriterion.contains(getTarget(), value, path);
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return JsonContainsCriterion.notContains(getTarget(), value, path);
    }

    @Override
    default Criterion like(String value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.LIKE)
                .value(value)
                .build();

    }

    @Override
    default Criterion notLike(String value) {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_LIKE)
                .value(value)
                .build();
    }

    @Override
    default Criterion isNull() {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.NULL)
                .build();
    }

    @Override
    default Criterion isNotNull() {
        return CompareCriterionOperation.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_NULL)
                .build();
    }


}