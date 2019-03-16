package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.DefaultCriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:25:39
 * @since 1.0
 */
public abstract class InCriterionOperation<E> extends AbsCollectionCriterionOperation<E> {
    @Override
    public final CriterionOperator operator() {
        return DefaultCriterionOperator.IN;
    }

}
