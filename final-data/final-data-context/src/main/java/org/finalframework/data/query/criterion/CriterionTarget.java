package org.finalframework.data.query.criterion;

import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.operation.Operation.CompareOperation;
import org.finalframework.data.query.operation.StringOperation;
import org.finalframework.data.query.operation.function.Function;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-27 16:31:17
 * @since 1.0
 */
public interface CriterionTarget<T, V> extends CriterionValue<T, CriterionTarget<T, V>>, Criteriable<Object, Criterion> {

    static <V> CriterionTarget<Object, Object> from(Object target) {
        return target instanceof CriterionTarget ? (CriterionTarget<Object, Object>) target : new CriterionValueImpl<>(target);
    }

    static <V> CriterionTarget<QProperty<V>, Object> from(QProperty<V> property) {
        return new CriterionValueImpl<>(property);
    }

    static <T> CriterionTarget<QProperty<T>, Object> from(QProperty<T> property, Function function) {
        final CriterionValueImpl<QProperty<T>, Object> criterionValue = new CriterionValueImpl<>(property);
        return criterionValue.apply(function);
    }

    @Override
    default Criterion between(Object min, Object max) {
        return BetweenCriterion.builder()
                .target(this)
                .operation(CompareOperation.BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    default Criterion notBetween(Object min, Object max) {
        return BetweenCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    default Criterion eq(Object value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion neq(Object value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion gt(Object value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.GREAT_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion gte(Object value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.GREAT_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion lt(Object value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.LESS_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion lte(Object value) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.LESS_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion in(Collection<Object> values) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion nin(Collection<Object> values) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion jsonContains(Object value, String path) {
        return JsonContainsCriterion.contains(this, value, path);


//        return SingleCriterion.builder()
//                .target(this.apply(JsonOperation.contains(value, path)))
//                .operation(CompareOperation.EQUAL)
//                .value(true)
//                .build();
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return JsonContainsCriterion.contains(this, value, path);
//        return SingleCriterion.builder()
//                .target(this.apply(JsonOperation.contains(value, path)))
//                .operation(CompareOperation.NOT_BETWEEN)
//                .value(false)
//                .build();
    }

    @Override
    default Criterion like(String prefix, String value, String suffix) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.LIKE)
                .value(CriterionValue.from(value).apply(StringOperation.concat(prefix, suffix)))
                .build();
    }

    @Override
    default Criterion notLike(String prefix, String value, String suffix) {
        return SingleCriterion.builder()
                .target(this)
                .operation(CompareOperation.NOT_LIKE)
                .value(CriterionValue.from(value).apply(StringOperation.concat(prefix, suffix)))
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