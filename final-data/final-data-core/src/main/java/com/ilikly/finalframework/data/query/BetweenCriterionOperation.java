package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 */
public interface BetweenCriterionOperation<T extends Comparable<T>> extends CriterionOperation<T, BetweenCriterion<T>> {
    @Override
    default String format(BetweenCriterion<T> criterion) {
        return format(criterion.property(), criterion.operator(), criterion.min(), criterion.max());
    }

    String format(QProperty property, CriterionOperator operator, T min, T max);
}
