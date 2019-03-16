package org.finalframework.data.query.criterion;


import org.finalframework.data.query.criterion.operation.*;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 23:03:19
 * @since 1.0
 */
public interface SimpleCriterionOperators<T> extends CriterionOperators<T> {

    @Nullable
    EqualCriterionOperation<T> eq();

    @Nullable
    NotEqualCriterionOperation<T> neq();

    @Nullable
    GreaterThanCriterionOperation<T> gt();

    @Nullable
    GreaterThanEqualCriterionOperation<T> gte();

    @Nullable
    LessThanCriterionOperation<T> lt();

    @Nullable
    LessThanEqualCriterionOperation<T> lte();

    @Nullable
    InCriterionOperation<T> in();

    @Nullable
    NotInCriterionOperation<T> nin();

    @Nullable
    default NullCriterionOperation<T> isNull() {
        return new NullCriterionOperation<>();
    }

    @Nullable
    default NotNullCriterionOperation<T> nonNull() {
        return new NotNullCriterionOperation<>();
    }

    @Nullable
    BetweenCriterionOperation<T> between();

    @Nullable
    NotBetweenCriterionOperation<T> notBetween();

}
