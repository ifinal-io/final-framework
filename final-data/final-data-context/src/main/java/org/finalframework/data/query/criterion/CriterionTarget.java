package org.finalframework.data.query.criterion;

import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.operation.Operation.CompareOperation;
import org.finalframework.data.query.operation.StringOperation;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-27 16:31:17
 * @since 1.0
 */
public interface CriterionTarget<T, V> extends CriterionValue<T, CriterionTarget<T, V>>, Criteriable<V, Criterion> {

    static <V> CriterionTarget<QProperty<V>, V> from(QProperty<V> property) {
        return new CriterionValueImpl<>(property);
    }

    @Override
    default Criterion between(V min, V max) {
        return BetweenCriterion.builder()
                .target(this)
                .operation(CompareOperation.BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    default Criterion notBetween(V min, V max) {
        return BetweenCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    default Criterion eq(V value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion neq(V value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion gt(V value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.GREAT_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion gte(V value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.GREAT_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion lt(V value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.LESS_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion lte(V value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.LESS_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion in(Collection<V> values) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion nin(Collection<V> values) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion jsonContains(Object value, String path) {
        return null;
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return null;
    }

    @Override
    default Criterion like(String prefix, String value, String suffix) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.LIKE)
                .value(CriterionValue.from(value).apply(() -> StringOperation.concat(prefix, suffix)))
                .build();
    }

    @Override
    default Criterion notLike(String prefix, String value, String suffix) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_LIKE)
                .value(CriterionValue.from(value).apply(() -> StringOperation.concat(prefix, suffix)))
                .build();
    }

    @Override
    default Criterion isNull() {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NULL)
                .build();
    }

    @Override
    default Criterion isNotNull() {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_NULL)
                .build();
    }


}