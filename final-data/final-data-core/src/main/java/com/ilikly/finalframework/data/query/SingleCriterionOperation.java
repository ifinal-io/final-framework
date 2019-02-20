package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 */
public interface SingleCriterionOperation<T> extends CriterionOperation<T, SingleCriterion<T>> {

    @Override
    default String format(SingleCriterion<T> criterion) {
        return format(criterion.property(), criterion.operator(), criterion.value());
    }

    String format(QProperty property, CriterionOperator operator, T value);
}
