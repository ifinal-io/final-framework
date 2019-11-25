package org.finalframework.data.query.criterion;

import org.finalframework.data.query.CriterionOperation;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 */
public interface CollectionCriterionOperation<T> extends CriterionOperation<Collection<T>, SingleCriterion<Collection<T>>> {

    @Override
    default String format(SingleCriterion<Collection<T>> criterion) {
        return format(criterion.getProperty(), criterion.getFunctions(), criterion.getOperator(), criterion.getValue());
    }

    String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<T> value);
}
