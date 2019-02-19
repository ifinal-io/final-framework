package com.ilikly.finalframework.data.query;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 */
public interface CollectionCriterionOperation<T> extends CriterionOperation<Collection<T>, CollectionCriterion<T>> {

    @Override
    default String format(CollectionCriterion<T> criterion) {
        return format(criterion.property(), criterion.operation(), criterion.value());
    }

    String format(QProperty property, String operation, Collection<T> value);
}
