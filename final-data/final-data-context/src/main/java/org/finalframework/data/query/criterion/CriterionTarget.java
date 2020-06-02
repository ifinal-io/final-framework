package org.finalframework.data.query.criterion;

import org.finalframework.data.query.criteriable.Criteriable;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.finalframework.data.query.operation.Operation.CompareOperation;
import org.finalframework.data.query.operation.StringOperation;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-27 16:31:17
 * @since 1.0
 */
public interface CriterionTarget<T> extends Criteriable<Object, Criterion> {

    static <T> CriterionTarget<T> from(T target) {
        return target instanceof CriterionTarget ? (CriterionTarget<T>) target : new CriterionTargetImpl<>(target);
    }

    T getTarget();

    default CriterionTarget<CriterionFunction> apply(Function<T, CriterionFunction> mapper) {
        return from(mapper.apply(getTarget()));
    }

    @Override
    default Criterion between(Object min, Object max) {
        return BetweenCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    default Criterion notBetween(Object min, Object max) {
        return BetweenCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_BETWEEN)
                .between(min, max)
                .build();
    }

    @Override
    default Criterion eq(Object value) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion neq(Object value) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion gt(Object value) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.GREAT_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion gte(Object value) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.GREAT_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion lt(Object value) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.LESS_THAN)
                .value(value)
                .build();
    }

    @Override
    default Criterion lte(Object value) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.LESS_THAN_EQUAL)
                .value(value)
                .build();
    }

    @Override
    default Criterion in(Collection<Object> values) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion nin(Collection<Object> values) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_IN)
                .value(values)
                .build();
    }

    @Override
    default Criterion jsonContains(Object value, String path) {
        return JsonContainsCriterion.contains(getTarget(), value, path);


//        return SingleCriterion.builder()
//                .target(this.apply(JsonOperation.contains(value, path)))
//                .operation(CompareOperation.EQUAL)
//                .value(true)
//                .build();
    }

    @Override
    default Criterion notJsonContains(Object value, String path) {
        return JsonContainsCriterion.contains(getTarget(), value, path);
//        return SingleCriterion.builder()
//                .target(this.apply(JsonOperation.contains(value, path)))
//                .operation(CompareOperation.NOT_BETWEEN)
//                .value(false)
//                .build();
    }

    @Override
    default Criterion like(String prefix, String value, String suffix) {
        final List<String> values = Stream.of(prefix, value, suffix).filter(Objects::nonNull).collect(Collectors.toList());
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.LIKE)
                .value(CriterionValue.from(values).apply(item -> new SimpleCriterionFunction(item, StringOperation.CONCAT)))
//                .value(CriterionValue.from(value).apply(StringOperation.concat(prefix, suffix)))
                .build();
    }

    @Override
    default Criterion notLike(String prefix, String value, String suffix) {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_LIKE)
//                .value(CriterionValue.from(value).apply(StringOperation.concat(prefix, suffix)))
                .build();
    }

    @Override
    default Criterion isNull() {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.NULL)
                .build();
    }

    @Override
    default Criterion isNotNull() {
        return SingleCriterion.builder()
                .target(getTarget())
                .operation(CompareOperation.NOT_NULL)
                .build();
    }


}