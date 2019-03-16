package org.finalframework.data.query.criterion;

import org.finalframework.data.query.criterion.operation.*;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 23:21:17
 * @since 1.0
 */
public interface LikeCriterionOperations<T> extends SimpleCriterionOperators<T> {

    @Nullable
    StartWithCriterionOperation<T> startWith();

    @Nullable
    NotStartWithCriterionOperation<T> notStartWith();

    @Nullable
    EndWithCriterionOperation<T> endWith();

    @Nullable
    NotEndWithCriterionOperation<T> notEndWith();

    @Nullable
    ContainsCriterionOperation<T> contains();

    @Nullable
    NotContainsCriterionOperation<T> notContains();

    @Nullable
    LikeCriterionOperation<T> like();

    @Nullable
    NotLikeCriterionOperation<T> notLike();
}