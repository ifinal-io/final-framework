package com.ilikly.finalframework.data.query.criterion;

import com.ilikly.finalframework.data.query.CriterionOperation;
import com.ilikly.finalframework.data.query.QProperty;

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
        return format(criterion.property(), criterion.operator(), criterion.value());
    }

    String format(QProperty property, CriterionOperator operator, Collection<T> value);
}
