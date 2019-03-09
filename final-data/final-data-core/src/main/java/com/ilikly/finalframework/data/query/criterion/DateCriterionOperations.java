package com.ilikly.finalframework.data.query.criterion;

import com.ilikly.finalframework.data.query.criterion.operation.*;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 23:26:00
 * @since 1.0
 */
public interface DateCriterionOperations<T> extends SimpleCriterionOperators<T> {
    @Nullable
    BeforeCriterionOperation<T> before();

    @Nullable
    AfterCriterionOperation<T> after();

    @Nullable
    DateEqualCriterionOperation<T> dateEqual();

    @Nullable
    NotDateEqualCriterionOperation<T> notDateEqual();

    @Nullable
    DateBeforeCriterionOperation<T> dateBefore();

    @Nullable
    DateAfterCriterionOperation<T> dateAfter();

    @Nullable
    DateBetweenCriterionOperation<T> dateBetween();

    @Nullable
    NotDateBetweenCriterionOperation<T> notDateBetween();
}
