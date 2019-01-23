package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 */
public interface CriterionOperation<T> {

    String name();

    default String format(Criterion<T> criterion) {
        if (criterion.value() != null) {
            return format(criterion.property(), criterion.value());
        } else {
            return format(criterion.property(), criterion.min(), criterion.max());
        }
    }

    String format(QProperty property, T value);

    String format(QProperty property, T min, T max);


}
